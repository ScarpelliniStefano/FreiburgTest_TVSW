asm modelFreiburgTest_scenari

import StandardLibrary


signature:
// DOMAINS
	abstract domain CertifierStatus
	enum domain Control={RICHIESTA_POSIZIONE, CONTROLLO_RISPOSTA,GENERA_NUOVA_RISPOSTA}
	enum domain ControlRW={INIZIO_RW,SETTAGGI_LEFT_OR_RIGHT,SETTAGGI_PROF_CURR,CONTROLLO_FINE}
	enum domain Result={CONTINUA,FINE_CERTIFICATA,FINE_NON_CERTIFICATA}
	enum domain PositionRight={FORWARD,BEHIND}
	enum domain Posizione={AVANTI,INDIETRO,ESCI}
	enum domain Soluzione={GIUSTA, SBAGLIATA,STOP}
	enum domain ResetDom={RESET_MINMAX,RESET_ASSIGN,STARTED}
		
	domain Livello subsetof Integer
	domain LivelloBis subsetof Integer
	domain ChanceTimes subsetof Integer


// FUNCTIONS
	static ceil : Real -> Integer
	derived livBisToLiv : LivelloBis -> Livello
	
	dynamic monitored posizioneScelta : Posizione
	dynamic monitored rifai : Boolean	
	monitored limiteMin : LivelloBis
	monitored limiteMax : LivelloBis
	
	dynamic controlled continuaTest : Boolean
	dynamic controlled outMessage : CertifierStatus -> Result
	dynamic controlled posizioneGiusta : Posizione
	dynamic controlled livelloTest : Livello
	dynamic controlled livelloCertificato : CertifierStatus -> Livello
	dynamic controlled chance : ChanceTimes
	dynamic controlled rightLimit : Livello
	dynamic controlled leftLimit : Livello
	dynamic controlled sol : Soluzione
	dynamic controlled maxDepth : Livello
	dynamic controlled currentDepth : Livello
	dynamic controlled control : Control
	dynamic controlled controlRightWrong : ControlRW
	dynamic controlled reset: ResetDom
	dynamic controlled testCert : CertifierStatus
	
  
definitions:
// DOMAIN DEFINITIONS
	domain Livello={1 : 13} //13 non certificato 1..12 livello certificato
	domain LivelloBis={1 : 12}
	domain ChanceTimes={0:1}

// FUNCTION DEFINITIONS
	 function ceil($numberR in Real)= if itor(rtoi($numberR))>=$numberR then rtoi($numberR) else rtoi($numberR)+1 endif
	 function livBisToLiv($numb in LivelloBis)=$numb
	 	
	   
// RULE DEFINITIONS
	 rule r_generaRisp=
		//if posizioneGiusta=AVANTI then posizioneGiusta:=INDIETRO else posizioneGiusta:=AVANTI endif
   			posizioneGiusta:=INDIETRO //semplificazione per rendere deterministico il valore della posizione giusta
   			
	rule r_esci=
		par
			continuaTest:=false
			livelloCertificato(testCert):=13
			livelloTest:=currentDepth
			outMessage(testCert):=FINE_NON_CERTIFICATA
		endpar
	

		rule r_answerChange=

							if (leftLimit-rightLimit)<=1 then
								par
									outMessage(testCert):=FINE_CERTIFICATA
									currentDepth:=leftLimit
									livelloCertificato(testCert):=leftLimit
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
						currentDepth:=floor(div(leftLimit+rightLimit,2))
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
						currentDepth:=ceil(div(leftLimit+rightLimit,2))
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
						outMessage(testCert):=FINE_NON_CERTIFICATA
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
							if livelloCertificato(testCert)!=13 then
								livelloCertificato(testCert):=13
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
							if reset!=RESET_MINMAX then
								reset:=RESET_MINMAX
							endif
							outMessage(testCert):=CONTINUA
						endpar
					else
						continuaTest:=false
					endif
				endlet


// MAIN RULE
	main rule r_Main =
		if continuaTest then
			if reset=RESET_MINMAX then
					let ($max=limiteMax,$min=limiteMin) in 
						if $max>0 and $min<=$max then
							par
								leftLimit:=livBisToLiv($max) 
								rightLimit:=livBisToLiv($min)
								reset:=RESET_ASSIGN
							endpar
						endif
					endlet
			else
				if reset=RESET_ASSIGN then
					par
							if livelloTest!=leftLimit then
								livelloTest:=leftLimit
							endif
							if currentDepth!=leftLimit then
								currentDepth:=leftLimit
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
							reset:=STARTED
						endpar
				else
					r_test[]	
				endif
			endif
		else
			par
				reset:=RESET_MINMAX
				r_exit[]
			endpar
		endif
		
		
// INITIAL STATE
default init s0:
	function continuaTest = true
	function livelloTest=12
	function livelloCertificato($t in CertifierStatus)=13
	function chance=1
	function sol=GIUSTA
	function currentDepth=12
	function maxDepth=12
	function control=RICHIESTA_POSIZIONE
	function controlRightWrong=INIZIO_RW
	function posizioneGiusta=INDIETRO
	function rightLimit=1
	function leftLimit=12
	function reset=RESET_MINMAX
	   
