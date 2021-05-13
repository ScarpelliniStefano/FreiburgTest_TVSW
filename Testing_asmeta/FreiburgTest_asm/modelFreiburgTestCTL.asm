asm modelFreiburgTestCTL

import StandardLibrary
import CTLlibrary


signature:
// DOMAINS
	enum domain Control={RICHIESTA_POSIZIONE, CONTROLLO_RISPOSTA,GENERA_NUOVA_RISPOSTA}
	enum domain ControlRW={INIZIO_RW,SETTAGGI_LEFT_OR_RIGHT,SETTAGGI_PROF_CURR,CONTROLLO_FINE}
	enum domain Result={CONTINUA,FINE_CERTIFICATA,FINE_NON_CERTIFICATA}
	enum domain PositionRight={FORWARD,BEHIND}
	enum domain Posizione={AVANTI,INDIETRO,ESCI}
	enum domain Soluzione={GIUSTA, SBAGLIATA,STOP}
		

	domain RealCeil subsetof Integer
	domain LivelloCert subsetof Integer
	domain Livello subsetof Integer
	domain ChanceTimes subsetof Integer


// FUNCTIONS
	static ceil : RealCeil -> Livello
	derived livToLivCert : Livello -> LivelloCert
	//derived livCertToLiv : LivelloCert -> Livello
	derived intToLivello : Integer -> Livello
	derived realCeilToReal : RealCeil -> Integer
	derived realToRealCeil : Integer -> RealCeil
	
	derived limitSuper:Boolean
	
	
	dynamic monitored posizioneScelta : Posizione
	dynamic monitored rifai : Boolean	
	//monitored limiteMin : Livello
	//monitored limiteMax : Livello
	
	dynamic controlled continuaTest : Boolean
	dynamic controlled outMessage : Result
	dynamic controlled posizioneGiusta : Posizione
	dynamic controlled livelloTest : Livello
	dynamic controlled livelloCertificato : LivelloCert
	dynamic controlled chance : ChanceTimes
	dynamic controlled rightLimit : Livello
	dynamic controlled leftLimit : Livello
	dynamic controlled sol : Soluzione
	dynamic controlled maxDepth : Livello
	dynamic controlled currentDepth : Livello
	dynamic controlled control : Control
	dynamic controlled controlRightWrong : ControlRW
	
  
definitions:
// DOMAIN DEFINITIONS
	domain LivelloCert={+1 : +13} //13 non certificato 1..12 livello certificato
	domain Livello={+1 : +12}
	domain RealCeil={+1 : +30}
	domain ChanceTimes={+0:+1}




// FUNCTION DEFINITIONS
	 function ceil($numberR in RealCeil)= if ge(itor(realCeilToReal($numberR)),itor(realCeilToReal($numberR))) then realCeilToReal($numberR) else realCeilToReal($numberR)+1 endif
	 function livToLivCert($numb in Livello)=$numb
	// function livCertToLiv($numb in LivelloCert)=$numb
	 function intToLivello($numb in Integer)=$numb
	 function realCeilToReal($numb in RealCeil)=$numb
	 function realToRealCeil($numb in Integer)=$numb
	 	
	 	
	 function limitSuper=leftLimit>=1 and rightLimit>=1 and leftLimit<=12 and rightLimit<=12
	   
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
	

		rule r_answerChange=

							if (leftLimit-rightLimit)<=1 then
								par
									outMessage:=FINE_CERTIFICATA
									currentDepth:=leftLimit
									livelloCertificato:=livToLivCert(leftLimit)
									continuaTest:=false
								endpar
							else
								par
								controlRightWrong:=CONTROLLO_FINE
								control:=GENERA_NUOVA_RISPOSTA
								endpar
							endif

		rule r_rightAnswer=
			if controlRightWrong=INIZIO_RW then
				par
					leftLimit:=currentDepth
					controlRightWrong:=SETTAGGI_LEFT_OR_RIGHT
				endpar
			else 
				if controlRightWrong=SETTAGGI_LEFT_OR_RIGHT then
					par
						//currentDepth:=floor(div(leftLimit+rightLimit,2))
						if(leftLimit>rightLimit) then
							currentDepth:=leftLimit-1 
						else
							currentDepth:=leftLimit
						endif
						controlRightWrong:=SETTAGGI_PROF_CURR
					endpar
				else 
					if controlRightWrong=SETTAGGI_PROF_CURR then
						r_answerChange[]
					endif
				endif
			endif
					
	rule r_wrongAnswer=
			if controlRightWrong=INIZIO_RW then
				par
					rightLimit:=currentDepth
					controlRightWrong:=SETTAGGI_LEFT_OR_RIGHT
				endpar
			else 
				if controlRightWrong=SETTAGGI_LEFT_OR_RIGHT then
					par
						//currentDepth:=floor(div(leftLimit+rightLimit,2))
						if(leftLimit>rightLimit) then
							currentDepth:=rightLimit+1 
						else
							currentDepth:=leftLimit
						endif
						controlRightWrong:=SETTAGGI_PROF_CURR
					endpar
				else 
					if controlRightWrong=SETTAGGI_PROF_CURR then
						r_answerChange[]
					endif
				endif
			endif
					
	

	rule r_controlloRisposta($a in Soluzione)=
		if control=CONTROLLO_RISPOSTA then
			if ($a = SBAGLIATA and chance>0 and currentDepth=maxDepth) then
				par
					chance:=chance-1
					control:=GENERA_NUOVA_RISPOSTA
					sol:=GIUSTA
				endpar
			else 
				if ($a = SBAGLIATA and chance=0 and currentDepth=maxDepth) or ($a=STOP) then
					par
						outMessage:=FINE_NON_CERTIFICATA
						continuaTest:=false
					endpar
				else 
					if $a=GIUSTA then
						r_rightAnswer[]
					else
						r_wrongAnswer[]
					endif
				endif
			endif
		else 
			if control=GENERA_NUOVA_RISPOSTA and (controlRightWrong=INIZIO_RW or controlRightWrong=CONTROLLO_FINE) then
				par
					r_generaRisp[]
					control:=RICHIESTA_POSIZIONE
					controlRightWrong:=INIZIO_RW
				endpar
			endif
			
		endif

	rule r_test=	
		if control=RICHIESTA_POSIZIONE then
			let ($p=posizioneScelta) in
				if ($p=ESCI) then
					r_esci[]
				else
					par
						if $p=posizioneGiusta then
							sol:=GIUSTA
						else
							sol:=SBAGLIATA
						endif
						control:=CONTROLLO_RISPOSTA
					endpar
				endif
			endlet
		else 
			r_controlloRisposta[sol]
		endif



	rule r_exit=
				let($s=rifai) in
					if $s then
						par
							continuaTest:=true
							if livelloTest!=12 then
								livelloTest:=12
							endif
							if livelloCertificato!=13 then
								livelloCertificato:=13
							endif
							if chance!=1 then
								chance:=1
							endif
							if sol!=GIUSTA then
								sol:=GIUSTA
							endif
							if currentDepth!=12 then
								currentDepth:=12
							endif
							if maxDepth!=12 then
								maxDepth:=12
							endif
							if control!=RICHIESTA_POSIZIONE then
								control:=RICHIESTA_POSIZIONE
							endif
							if controlRightWrong!=INIZIO_RW then
								controlRightWrong:=INIZIO_RW
							endif
							if posizioneGiusta!=INDIETRO then
								posizioneGiusta:=INDIETRO
							endif
							if rightLimit!=1 then
								rightLimit:=1
							endif
							if leftLimit!=12 then
								leftLimit:=12
							endif
							outMessage:=CONTINUA
						endpar
					else
						continuaTest:=false
					endif
				endlet


//esiste un caso in cui si giunge a FINE_CERTIFICATA
CTLSPEC ef(outMessage=FINE_CERTIFICATA) 
//esiste un caso in cui si giunge a FINE_NON_CERTIFICATA
CTLSPEC ef(outMessage=FINE_CERTIFICATA) 
//in ogni caso FINE_NON_CERTIFICATA O FINE_CERTIFICATA implica che ci sarà continuaTest=false
CTLSPEC ag(outMessage=FINE_NON_CERTIFICATA or outMessage=FINE_CERTIFICATA implies continuaTest=false)
//per ogni caso in cui sol è SBAGLIATA e chance=0 e currentDepth=maxDepth avremo che nel futuro ci sarà outMessage=FINE_NON_CERTIFICATA
CTLSPEC af(sol=SBAGLIATA and chance=0 and currentDepth=maxDepth implies af(outMessage=FINE_NON_CERTIFICATA))
//in nessuno stato deve esserci un valore di leftLimit e rightLimit esterno a 1 e 12
CTLSPEC ag(not limitSuper)

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
	function chance=1
	function sol=GIUSTA
	function currentDepth=12
	function maxDepth=12
	function control=RICHIESTA_POSIZIONE
	function controlRightWrong=INIZIO_RW
	function posizioneGiusta=INDIETRO
	function rightLimit=1
	function leftLimit=12