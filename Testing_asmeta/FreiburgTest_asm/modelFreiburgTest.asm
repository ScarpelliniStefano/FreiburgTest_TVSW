asm modelFreiburgTest

import StandardLibrary


signature:
// DOMAINS
	enum domain Result={CONTINUA,FINE_CERTIFICATA,FINE_NON_CERTIFICATA}
	enum domain Scelta={SALTA,FINISCI,CORRETTO,SBAGLIATO}
	enum domain PositionRight={FORWARD,BEHIND}
	enum domain Posizione={AVANTI,INDIETRO,ESCI}
	enum domain Soluzione={GIUSTA, SBAGLIATA,STOP}
	domain Livello subsetof Integer
	domain Certificato subsetof Integer
	domain SkippedTimes subsetof Integer
	domain ChanceTimes subsetof Integer


// FUNCTIONS
	dynamic controlled continuaTest: Boolean
	dynamic controlled outMessage: Result
	dynamic monitored posizioneScelta : Posizione
	dynamic controlled posizioneGiusta: Posizione
	dynamic controlled livelloTest: Livello
	dynamic controlled livelloCertificato: Certificato
	dynamic controlled erroreRisposta: Boolean 
	dynamic controlled skippedTimes : SkippedTimes
	dynamic controlled chance: ChanceTimes
	dynamic controlled angolo : Real

	dynamic controlled rightLimit : Livello
	dynamic controlled leftLimit : Livello
	dynamic controlled sol : Soluzione
	dynamic controlled maxDepth : Livello
	dynamic controlled currentDepth : Livello
	
	static ceil : Real -> Integer
  
definitions:
// DOMAIN DEFINITIONS
	domain Livello={1 : 12}
	domain Certificato={1 : 13} //13 non certificato 1..12 livello certificato
	//?? se scelto dall'utente livMin e livMax?
	domain SkippedTimes={0:2}
	domain ChanceTimes={0:1}

// FUNCTION DEFINITIONS
	 
	 function ceil($numberR in Real)= if ge(itor(round($numberR)),$numberR) then rtoi($numberR) else rtoi($numberR)+1 endif
	 
	   
// RULE DEFINITIONS
	rule r_generaRisp=
		choose $x in PositionRight with true do
    	if $x = FORWARD then posizioneGiusta:=AVANTI else posizioneGiusta:=INDIETRO endif

	rule r_esci=
		par
			continuaTest:=false
			livelloCertificato:=13
			livelloTest:=currentDepth
			outMessage:=FINE_NON_CERTIFICATA
		endpar
	

		rule r_rightAnswer=
					par
						leftLimit:=currentDepth
						currentDepth:=floor(div(leftLimit+rightLimit,2))
					endpar
					if (leftLimit-rightLimit)<=1 then
						par
							outMessage:=FINE_CERTIFICATA
							currentDepth:=leftLimit
							continuaTest:=true
							livelloCertificato:=leftLimit
						endpar
					else
						outMessage:=CONTINUA
					endif
					
	rule r_wrongAnswer=
					par
						rightLimit:=currentDepth
						currentDepth:=ceil(div(leftLimit+rightLimit,2))
					endpar
					if (leftLimit-rightLimit)<=1 then
						par
							outMessage:=FINE_CERTIFICATA
							currentDepth:=leftLimit
							continuaTest:=true
							livelloCertificato:=leftLimit
						endpar
					else
						outMessage:=CONTINUA
					endif
					


	rule r_controlloRisposta($a in Soluzione)=
		if ($a = SBAGLIATA and chance>0 and currentDepth=maxDepth) then
			chance:=chance-1
		else 
			if ($a = SBAGLIATA and chance=0 and currentDepth=maxDepth) or ($a=STOP) then
				outMessage:=FINE_NON_CERTIFICATA
			else 
				if $a=GIUSTA then
					r_rightAnswer[]
				else
					r_wrongAnswer[]
				endif
				r_generaRisp[]
			endif
		endif


	rule r_test=	
		let ($p=posizioneScelta) in
				if ($p=ESCI) then
					r_esci[]
				else
						if $p=posizioneGiusta then
							sol:=GIUSTA
						else
							sol:=SBAGLIATA
						endif
						r_controlloRisposta[sol]
				endif
		endlet



	rule r_exit=
			if livelloCertificato=13 then
				outMessage:=FINE_NON_CERTIFICATA
			else
		   		outMessage:=FINE_CERTIFICATA
			endif







// MAIN RULE
	main rule r_Main =
		if continuaTest then
			r_test[]	
		else

			r_exit[]
		endif
		
		
// INITIAL STATE

default init s0:
	function continuaTest = true
	function livelloTest=12
	function livelloCertificato=13
	function erroreRisposta=false
	function chance=1
	function angolo=0.0
	function sol=GIUSTA
	function currentDepth=12