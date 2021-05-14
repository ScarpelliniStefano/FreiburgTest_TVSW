asm modelFreiburgTestCTL

import StandardLibrary
import CTLlibrary


signature:
// DOMAINS
	
	enum domain Control={RICHIESTA_POSIZIONE, CONTROLLO_RISPOSTA,GENERA_NUOVA_RISPOSTA}
	enum domain ControlRW={INIZIO_RW,SETTAGGI_LEFT_OR_RIGHT,SETTAGGI_PROF_CURR,CONTROLLO_FINE}
	enum domain Result={CONTINUA,FINE_CERTIFICATA,FINE_NON_CERTIFICATA}
	enum domain Posizione={AVANTI,INDIETRO,ESCI}
	enum domain PosizioneGiusta={AVANTIG,INDIETROG}
	enum domain Soluzione={GIUSTA, SBAGLIATA,STOP}
	enum domain ChanceTimes={UNA, NESSUNA}
	enum domain ResetDom={RESET_MINMAX,STARTED}

	enum domain Livello={UNO, DUE, TRE, QUATTRO, CINQUE}
	//domain RealCeil subsetof Integer
	enum domain LivelloCert={TRECERT, CINQUE_NCERT}
	//domain Livello subsetof Integer


// FUNCTIONS
	//static ceil : Livello -> Livello
	//static livToLivCert:Livello->LivelloCert
	
	//derived limitSuper:Boolean
	
	
	dynamic monitored posizioneScelta : Posizione
	dynamic monitored rifai : Boolean	
	monitored limiteMin : Livello
	monitored limiteMax : Livello
	
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
	dynamic controlled reset: ResetDom
	dynamic controlled posG:PosizioneGiusta
  
definitions:
// DOMAIN DEFINITIONS
	//domain LivelloCert={+1 : +13} //13 non certificato 1..12 livello certificato
	//domain Livello={+1 : +13}
	//domain RealCeil={+1 : +30}




// FUNCTION DEFINITIONS
	 //function ceil($numberR in Livello)= if $numberR>=$numberR then $numberR else $numberR+1 endif
	 //function livToLivCert($numb in Livello)=$numb
	// function livCertToLiv($numb in LivelloCert)=$numb
	 //function intToLivello($numb in Integer)=$numb
	 //function realCeilToReal($numb in RealCeil)=$numb
	 //function realToRealCeil($numb in Integer)=$numb
	 	
	 	
	// function limitSuper=leftLimit!=UNO and rightLimit>=1 and leftLimit<=12 and rightLimit<=12
	   
// RULE DEFINITIONS
	rule r_generaRisp=
		choose $x in PosizioneGiusta with true do
    	if $x = AVANTIG then 
    		par
    			posizioneGiusta:=AVANTI 
    			posG:=AVANTIG
    		endpar
    		else
    		par
    			posG:=INDIETROG
    			posizioneGiusta:=INDIETRO 
    		endpar
    		endif
   
	rule r_esci=
		par
			continuaTest:=false
			livelloCertificato:=CINQUE_NCERT
			livelloTest:=currentDepth
			outMessage:=FINE_NON_CERTIFICATA
			sol:=STOP
		endpar
	

		rule r_answerChange=
							if leftLimit=TRE and rightLimit=DUE then
								par
									outMessage:=FINE_CERTIFICATA
									currentDepth:=leftLimit
									livelloCertificato:=TRECERT
									continuaTest:=false
									posizioneGiusta:=ESCI
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
						if(leftLimit=TRE)then
							currentDepth:=DUE
						else
							currentDepth:=TRE
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
						//currentDepth:=ceil(rtoi(div(leftLimit+rightLimit,2)))
						if(rightLimit=TRE)then
							currentDepth:=DUE
						else
							currentDepth:=TRE
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
			if ($a = SBAGLIATA and chance=UNA and currentDepth=maxDepth) then
				par
					chance:=NESSUNA
					control:=GENERA_NUOVA_RISPOSTA
					sol:=GIUSTA
				endpar
			else 
				if ($a = SBAGLIATA and chance=NESSUNA and currentDepth=maxDepth) or ($a=STOP) then
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
							if livelloTest!=QUATTRO then
								livelloTest:=QUATTRO
							endif
							if livelloCertificato!=CINQUE_NCERT then
								livelloCertificato:=CINQUE_NCERT
							endif
							if chance!=UNA then
								chance:=UNA
							endif
							if sol!=GIUSTA then
								sol:=GIUSTA
							endif
							if currentDepth!=QUATTRO then
								currentDepth:=QUATTRO
							endif
							if maxDepth!=QUATTRO then
								maxDepth:=QUATTRO
							endif
							if control!=RICHIESTA_POSIZIONE then
								control:=RICHIESTA_POSIZIONE
							endif
							if controlRightWrong!=INIZIO_RW then
								controlRightWrong:=INIZIO_RW
							endif
							if posizioneGiusta!=INDIETRO then
								par
									posizioneGiusta:=INDIETRO
									posG:=INDIETROG
								endpar
							endif
							if rightLimit!=UNO then
								rightLimit:=UNO
							endif
							if leftLimit!=QUATTRO then
								leftLimit:=QUATTRO
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
CTLSPEC af(sol=SBAGLIATA and chance=NESSUNA and currentDepth=maxDepth implies ax(outMessage=FINE_NON_CERTIFICATA))
//non è vero che ogni caso in cui controlRightWrong sia iniziato fin quando diventa SETTAGGI_LEFT_OR_RIGHT  --> falsa
CTLSPEC not a(controlRightWrong=INIZIO_RW,controlRightWrong=SETTAGGI_LEFT_OR_RIGHT)
//è vero che ogni caso in cui controlRightWrong sia iniziato fin quando diventa SETTAGGI_LEFT_OR_RIGHT  --> falsa
CTLSPEC a(controlRightWrong=INIZIO_RW,controlRightWrong=SETTAGGI_LEFT_OR_RIGHT)
//in ogni stato deve esserci una soluzione sbagliata, giusta o stop
CTLSPEC ag(sol=SBAGLIATA or sol=GIUSTA or sol=STOP)
//in ogni caso in cui posizioneScelta non è uguale a posizioneGiusta che porti nello stato successivo a avere FINE_NON_CERTIFICATA e che in ogni futuro di quello stato continuaTest sia falso
CTLSPEC af(posizioneScelta!=posizioneGiusta implies ax(outMessage=FINE_NON_CERTIFICATA and af(continuaTest=false)))


// MAIN RULE
	main rule r_Main =
		if continuaTest then
			if reset=RESET_MINMAX then
					let ($max=limiteMax,$min=limiteMin) in 
							par
								leftLimit:=$max
								rightLimit:=$min
								reset:=STARTED
							endpar
					endlet
			else
					r_test[]	
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
	function livelloTest=QUATTRO
	function livelloCertificato=CINQUE_NCERT
	function chance=UNA
	function sol=GIUSTA
	function currentDepth=limiteMax
	function maxDepth=limiteMax
	function control=RICHIESTA_POSIZIONE
	function controlRightWrong=INIZIO_RW
	function posizioneGiusta=INDIETRO
	function rightLimit=limiteMin
	function leftLimit=limiteMax
	function posG=INDIETROG