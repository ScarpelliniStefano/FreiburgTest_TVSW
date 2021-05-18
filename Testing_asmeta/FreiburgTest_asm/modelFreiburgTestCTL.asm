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

	enum domain Livello={UNO, DUE, TRE, QUATTRO, CINQUE, SEI, SETTE, OTTO, NOVE, DIECI, UNDICI, DODICI}
	enum domain LivelloCert={UNO_C, DUE_C, TRE_C, QUATTRO_C, CINQUE_C, SEI_C, SETTE_C, OTTO_C, NOVE_C, DIECI_C, UNDICI_C, DODICI_C,TREDICI_NC}


// FUNCTIONS
	
	dynamic monitored posizioneScelta : Posizione
	dynamic monitored rifai : Boolean	
	monitored limiteMin : Livello
	monitored limiteMax : Livello
	
	derived floor: Prod(Livello,Livello) -> Livello
	derived ceil: Prod(Livello,Livello) -> Livello
	derived distanzaUno: Prod(Livello,Livello) -> Boolean
	derived livToCert: Livello -> LivelloCert
	
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
	   
	   
	   
	function floor($r in Livello, $l in Livello)=
		switch($l)
		case UNO:
			switch($r)
			case UNO : UNO
			case DUE : UNO
			case TRE : DUE 
			case QUATTRO : DUE 
			case CINQUE : TRE 
			case SEI : TRE 
			case SETTE : QUATTRO 
			case OTTO : QUATTRO 
			case NOVE : CINQUE 
			case DIECI : CINQUE 
			case UNDICI : SEI 
			case DODICI : SEI 
			endswitch
		case DUE:
			switch($r)
			case UNO : UNO
			case DUE : DUE
			case TRE : DUE 
			case QUATTRO : TRE 
			case CINQUE : TRE 
			case SEI : QUATTRO 
			case SETTE : QUATTRO 
			case OTTO : CINQUE 
			case NOVE : CINQUE 
			case DIECI : SEI 
			case UNDICI : SEI 
			case DODICI : SETTE
			endswitch
		case TRE:
			switch($r)
			case UNO : DUE
			case DUE : DUE
			case TRE : TRE 
			case QUATTRO : TRE 
			case CINQUE : QUATTRO
			case SEI : QUATTRO 
			case SETTE : CINQUE 
			case OTTO : CINQUE 
			case NOVE : SEI 
			case DIECI : SEI 
			case UNDICI : SETTE 
			case DODICI : SETTE
			endswitch
		case QUATTRO:
			switch($r)
			case UNO : DUE
			case DUE : TRE
			case TRE : TRE 
			case QUATTRO : QUATTRO 
			case CINQUE : QUATTRO 
			case SEI : CINQUE 
			case SETTE : CINQUE 
			case OTTO : SEI 
			case NOVE : SEI 
			case DIECI : SETTE 
			case UNDICI : SETTE 
			case DODICI : OTTO 
			endswitch
		case CINQUE:
			switch($r)
			case UNO : TRE
			case DUE : TRE
			case TRE : QUATTRO 
			case QUATTRO : QUATTRO 
			case CINQUE : CINQUE 
			case SEI : CINQUE 
			case SETTE : SEI 
			case OTTO : SEI 
			case NOVE : SETTE 
			case DIECI : SETTE 
			case UNDICI : OTTO 
			case DODICI : OTTO 
			endswitch
		case SEI:
			switch($r)
			case UNO : TRE
			case DUE : QUATTRO
			case TRE : QUATTRO 
			case QUATTRO : CINQUE
			case CINQUE : CINQUE 
			case SEI : SEI 
			case SETTE : SEI 
			case OTTO : SETTE 
			case NOVE : SETTE 
			case DIECI : OTTO 
			case UNDICI : OTTO 
			case DODICI : NOVE  
			endswitch
		case SETTE:
			switch($r)
			case UNO : QUATTRO
			case DUE : QUATTRO
			case TRE : CINQUE 
			case QUATTRO : CINQUE
			case CINQUE : SEI 
			case SEI : SEI 
			case SETTE : SETTE 
			case OTTO : SETTE 
			case NOVE : OTTO 
			case DIECI : OTTO 
			case UNDICI : NOVE 
			case DODICI : NOVE
			endswitch
		case OTTO:
			switch($r)
			case UNO : QUATTRO
			case DUE : CINQUE
			case TRE : CINQUE 
			case QUATTRO : SEI
			case CINQUE : SEI 
			case SEI : SETTE 
			case SETTE : SETTE 
			case OTTO : OTTO 
			case NOVE : OTTO 
			case DIECI : NOVE 
			case UNDICI : NOVE 
			case DODICI : DIECI 
			endswitch
		case NOVE:
			switch($r)
			case UNO : CINQUE
			case DUE : CINQUE
			case TRE : SEI 
			case QUATTRO : SEI
			case CINQUE : SETTE 
			case SEI : SETTE 
			case SETTE : OTTO 
			case OTTO : OTTO 
			case NOVE : NOVE 
			case DIECI : NOVE 
			case UNDICI : DIECI 
			case DODICI : DIECI  
			endswitch
		case DIECI:
			switch($r)
			case UNO : CINQUE
			case DUE : SEI
			case TRE : SEI 
			case QUATTRO : SETTE
			case CINQUE : SETTE 
			case SEI : OTTO 
			case SETTE : OTTO 
			case OTTO : NOVE 
			case NOVE : NOVE 
			case DIECI : DIECI 
			case UNDICI : DIECI 
			case DODICI : UNDICI 
			endswitch
		case UNDICI:
			switch($r)
			case UNO : SEI
			case DUE : SEI
			case TRE : SETTE 
			case QUATTRO : SETTE
			case CINQUE : OTTO
			case SEI : OTTO 
			case SETTE : NOVE 
			case OTTO : NOVE 
			case NOVE : DIECI 
			case DIECI : DIECI 
			case UNDICI : UNDICI 
			case DODICI : UNDICI
			endswitch
		case DODICI:
			switch($r)
			case UNO : SEI
			case DUE : SETTE
			case TRE : SETTE 
			case QUATTRO : OTTO
			case CINQUE : OTTO
			case SEI : NOVE 
			case SETTE : NOVE 
			case OTTO : DIECI 
			case NOVE : DIECI 
			case DIECI : UNDICI 
			case UNDICI : UNDICI 
			case DODICI : DODICI
			endswitch
		endswitch
	function ceil($r in Livello, $l in Livello)=
		switch($l)
		case UNO:
			switch($r)
			case UNO : UNO
			case DUE : DUE
			case TRE : DUE 
			case QUATTRO : TRE 
			case CINQUE : TRE 
			case SEI : QUATTRO 
			case SETTE : QUATTRO 
			case OTTO : CINQUE 
			case NOVE : CINQUE 
			case DIECI : SEI 
			case UNDICI : SEI 
			case DODICI : SETTE 
			endswitch
		case DUE:
			switch($r)
			case UNO : DUE
			case DUE : DUE
			case TRE : TRE 
			case QUATTRO : TRE 
			case CINQUE : QUATTRO
			case SEI : QUATTRO 
			case SETTE : CINQUE 
			case OTTO : CINQUE 
			case NOVE : SEI 
			case DIECI : SEI 
			case UNDICI : SETTE 
			case DODICI : SETTE
			endswitch
		case TRE:
			switch($r)
			case UNO : DUE
			case DUE : TRE
			case TRE : TRE 
			case QUATTRO : QUATTRO 
			case CINQUE : QUATTRO 
			case SEI : CINQUE 
			case SETTE : CINQUE 
			case OTTO : SEI 
			case NOVE : SEI 
			case DIECI : SETTE 
			case UNDICI : SETTE 
			case DODICI : OTTO
			endswitch
		case QUATTRO:
			switch($r)
			case UNO : TRE
			case DUE : TRE
			case TRE : QUATTRO 
			case QUATTRO : QUATTRO 
			case CINQUE : CINQUE 
			case SEI : CINQUE 
			case SETTE : SEI 
			case OTTO : SEI 
			case NOVE : SETTE 
			case DIECI : SETTE 
			case UNDICI : OTTO 
			case DODICI : OTTO 
			endswitch
		case CINQUE:
			switch($r)
			case UNO : TRE
			case DUE : QUATTRO
			case TRE : QUATTRO 
			case QUATTRO : CINQUE
			case CINQUE : CINQUE 
			case SEI : SEI 
			case SETTE : SEI 
			case OTTO : SETTE 
			case NOVE : SETTE 
			case DIECI : OTTO 
			case UNDICI : OTTO 
			case DODICI : NOVE  
			endswitch
		case SEI:
			switch($r)
			case UNO : QUATTRO
			case DUE : QUATTRO
			case TRE : CINQUE 
			case QUATTRO : CINQUE
			case CINQUE : SEI 
			case SEI : SEI 
			case SETTE : SETTE 
			case OTTO : SETTE 
			case NOVE : OTTO 
			case DIECI : OTTO 
			case UNDICI : NOVE 
			case DODICI : NOVE  
			endswitch
		case SETTE:
			switch($r)
			case UNO : QUATTRO
			case DUE : CINQUE
			case TRE : CINQUE 
			case QUATTRO : SEI
			case CINQUE : SEI 
			case SEI : SETTE 
			case SETTE : SETTE 
			case OTTO : OTTO 
			case NOVE : OTTO 
			case DIECI : NOVE 
			case UNDICI : NOVE 
			case DODICI : DIECI 
			endswitch
		case OTTO:
			switch($r)
			case UNO : CINQUE
			case DUE : CINQUE
			case TRE : SEI 
			case QUATTRO : SEI
			case CINQUE : SETTE 
			case SEI : SETTE 
			case SETTE : OTTO 
			case OTTO : OTTO 
			case NOVE : NOVE 
			case DIECI : NOVE 
			case UNDICI : DIECI 
			case DODICI : DIECI 
			endswitch
		case NOVE:
			switch($r)
			case UNO : CINQUE
			case DUE : SEI
			case TRE : SEI 
			case QUATTRO : SETTE
			case CINQUE : SETTE 
			case SEI : OTTO 
			case SETTE : OTTO 
			case OTTO : NOVE 
			case NOVE : NOVE 
			case DIECI : DIECI 
			case UNDICI : DIECI 
			case DODICI : UNDICI 
			endswitch
		case DIECI:
			switch($r)
			case UNO : SEI
			case DUE : SEI
			case TRE : SETTE 
			case QUATTRO : SETTE
			case CINQUE : OTTO
			case SEI : OTTO 
			case SETTE : NOVE 
			case OTTO : NOVE 
			case NOVE : DIECI 
			case DIECI : DIECI 
			case UNDICI : UNDICI 
			case DODICI : UNDICI
			endswitch
		case UNDICI:
			switch($r)
			case UNO : SEI
			case DUE : SETTE
			case TRE : SETTE 
			case QUATTRO : OTTO
			case CINQUE : OTTO
			case SEI : NOVE 
			case SETTE : NOVE 
			case OTTO : DIECI 
			case NOVE : DIECI 
			case DIECI : UNDICI 
			case UNDICI : UNDICI 
			case DODICI : DODICI
			endswitch
		case DODICI:
			switch($r)
			case UNO : SETTE
			case DUE : SETTE
			case TRE : OTTO 
			case QUATTRO : OTTO
			case CINQUE : NOVE
			case SEI : NOVE 
			case SETTE : DIECI 
			case OTTO : DIECI 
			case NOVE : UNDICI 
			case DIECI : UNDICI 
			case UNDICI : DODICI
			case DODICI : DODICI
			endswitch
		endswitch		
	function distanzaUno($r in Livello, $l in Livello)=
		switch($l)
		case UNO:
			switch($r)
			case UNO : true
			case DUE : true
			otherwise false 
			endswitch
		case DUE:
			switch($r)
			case UNO :true
			case DUE : true
			case TRE : true
			otherwise false 
			endswitch
		case TRE:
			switch($r)
			case DUE:true
			case TRE : true
			case QUATTRO : true
			otherwise false 
			endswitch
		case QUATTRO:
			switch($r)
			case TRE :true
			case QUATTRO : true
			case CINQUE : true
			otherwise false 
			endswitch
		case CINQUE:
			switch($r)
			case QUATTRO :true
			case CINQUE : true
			case SEI : true
			otherwise false 
			endswitch
		case SEI:
			switch($r)
			case CINQUE :true
			case SEI : true
			case SETTE : true
			otherwise false   
			endswitch
		case SETTE:
			switch($r)
			case SEI :true
			case SETTE : true
			case OTTO : true
			otherwise false  
			endswitch
		case OTTO:
			switch($r)
			case SETTE :true
			case OTTO : true
			case NOVE : true
			otherwise false  
			endswitch
		case NOVE:
			switch($r)
			case OTTO :true
			case NOVE : true
			case DIECI : true
			otherwise false  
			endswitch
		case DIECI:
			switch($r)
			case NOVE :true
			case DIECI : true
			case UNDICI : true
			otherwise false 
			endswitch
		case UNDICI:
			switch($r)
			case DIECI :true
			case UNDICI : true
			case DODICI : true
			otherwise false 
			endswitch
		case DODICI:
			switch($r)
			case UNDICI :true
			case DODICI : true
			otherwise false 
			endswitch
		otherwise false
		endswitch		
	function livToCert($liv in Livello)=
	switch($liv)
			case UNO : UNO_C
			case DUE : DUE_C
			case TRE : TRE_C
			case QUATTRO : QUATTRO_C
			case CINQUE : CINQUE_C 
			case SEI : SEI_C
			case SETTE : SETTE_C 
			case OTTO : OTTO_C 
			case NOVE : NOVE_C 
			case DIECI : DIECI_C
			case UNDICI : UNDICI_C 
			case DODICI : DODICI_C
	endswitch

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
			livelloCertificato:=TREDICI_NC
			livelloTest:=currentDepth
			outMessage:=FINE_NON_CERTIFICATA
			sol:=STOP
		endpar
	

		rule r_answerChange=
							if distanzaUno(rightLimit,leftLimit) then
								par
									outMessage:=FINE_CERTIFICATA
									currentDepth:=leftLimit
									livelloCertificato:=livToCert(leftLimit)
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
			if controlRightWrong = INIZIO_RW then
				par
					leftLimit:=currentDepth
					controlRightWrong:=SETTAGGI_LEFT_OR_RIGHT
				endpar
			else 
				if controlRightWrong = SETTAGGI_LEFT_OR_RIGHT then
					par
						currentDepth:=floor(rightLimit,leftLimit)
						/*if(leftLimit=TRE)then
							currentDepth:=DUE
						else
							currentDepth:=TRE
						endif*/
						controlRightWrong:=SETTAGGI_PROF_CURR
					endpar
				else 
					if controlRightWrong = SETTAGGI_PROF_CURR then
						r_answerChange[]
					endif
				endif
			endif
					
	rule r_wrongAnswer=
			if controlRightWrong = INIZIO_RW then
				par
					rightLimit:=currentDepth
					controlRightWrong:=SETTAGGI_LEFT_OR_RIGHT
				endpar
			else 
				if controlRightWrong = SETTAGGI_LEFT_OR_RIGHT then
					par
						currentDepth:=ceil(rightLimit,leftLimit)
						/*if(rightLimit=TRE)then
							currentDepth:=DUE
						else
							currentDepth:=TRE
						endif*/
						
						controlRightWrong:=SETTAGGI_PROF_CURR
					endpar
				else 
					if controlRightWrong = SETTAGGI_PROF_CURR then
						r_answerChange[]
					endif
				endif
			endif
					
	

	rule r_controlloRisposta($a in Soluzione)=
		if control = CONTROLLO_RISPOSTA then
			if ($a = SBAGLIATA and chance = UNA and currentDepth = maxDepth) then
				par
					chance:=NESSUNA
					control:=GENERA_NUOVA_RISPOSTA
					sol:=GIUSTA
				endpar
			else 
				if ($a = SBAGLIATA and chance = NESSUNA and currentDepth = maxDepth) or ($a = STOP) then
					par
						outMessage:=FINE_NON_CERTIFICATA
						continuaTest:=false
					endpar
				else 
					if $a = GIUSTA then
						r_rightAnswer[]
					else
						r_wrongAnswer[]
					endif
				endif
			endif
		else 
			if control = GENERA_NUOVA_RISPOSTA and (controlRightWrong = INIZIO_RW or controlRightWrong = CONTROLLO_FINE) then
				par
					r_generaRisp[]
					control:=RICHIESTA_POSIZIONE
					controlRightWrong:=INIZIO_RW
				endpar
			endif
			
		endif

	rule r_test=	
		if control = RICHIESTA_POSIZIONE then
			let ($p = posizioneScelta) in
				if ($p = ESCI) then
					r_esci[]
				else
					par
						if $p = posizioneGiusta then
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
							if livelloTest!=DODICI then
								livelloTest:=DODICI
							endif
							if livelloCertificato!=TREDICI_NC then
								livelloCertificato:=TREDICI_NC
							endif
							if chance!=UNA then
								chance:=UNA
							endif
							if sol!=GIUSTA then
								sol:=GIUSTA
							endif
							if currentDepth!=DODICI then
								currentDepth:=DODICI
							endif
							if maxDepth!=DODICI then
								maxDepth:=DODICI
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
							if leftLimit!=DODICI then
								leftLimit:=DODICI
							endif
							outMessage:=CONTINUA
						endpar
					else
						continuaTest:=false
					endif
				endlet



//esiste sempre un caso in cui si giunge a FINE_CERTIFICATA
	CTLSPEC ef(outMessage = FINE_CERTIFICATA) 
//esiste sempre un caso in cui si giunge a FINE_NON_CERTIFICATA
	CTLSPEC ef(outMessage=FINE_NON_CERTIFICATA) 
//in ogni caso FINE_NON_CERTIFICATA O FINE_CERTIFICATA implica che ci sarà continuaTest=false
	CTLSPEC ag((outMessage=FINE_NON_CERTIFICATA or outMessage=FINE_CERTIFICATA) implies continuaTest=false)
//per ogni caso in cui sol è SBAGLIATA e chance=0 e currentDepth=maxDepth avremo che nel futuro ci sarà outMessage=FINE_NON_CERTIFICATA
	CTLSPEC af((sol=SBAGLIATA and chance=NESSUNA and currentDepth=maxDepth) implies ax(outMessage=FINE_NON_CERTIFICATA))
//ogni caso ha che controlRightWrong è all'inizio fin quando diventa SETTAGGI_LEFT_OR_RIGHT  --> falsa
	//CTLSPEC a(controlRightWrong=INIZIO_RW,controlRightWrong=SETTAGGI_LEFT_OR_RIGHT)
//non è vero che ogni caso ha che controlRightWrong è all'inizio fin quando diventa SETTAGGI_LEFT_OR_RIGHT
	CTLSPEC not a(controlRightWrong=INIZIO_RW,controlRightWrong=SETTAGGI_LEFT_OR_RIGHT)
//è vero che ogni caso in cui controlRightWrong sia iniziato fin quando diventa SETTAGGI_LEFT_OR_RIGHT o rimane INIZIO_RW
	CTLSPEC a(controlRightWrong=INIZIO_RW,(controlRightWrong=SETTAGGI_LEFT_OR_RIGHT or controlRightWrong=INIZIO_RW))
//in ogni stato deve esserci una soluzione sbagliata, giusta o stop
	CTLSPEC ag(sol=SBAGLIATA or sol=GIUSTA or sol=STOP)
//in ogni caso in cui posizioneScelta non è uguale a posizioneGiusta porta nello stato successivo a avere FINE_NON_CERTIFICATA e che in ogni futuro di quello stato continuaTest sia falso
	CTLSPEC af(posizioneScelta!=posizioneGiusta implies ax(outMessage=FINE_NON_CERTIFICATA and af(continuaTest=false)))
//esiste sempre un caso futuro in cui il test finisce con il livello certificato migliore
	CTLSPEC ef(outMessage = FINE_CERTIFICATA and livelloCertificato = DUE_C)

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
	function livelloTest = DODICI
	function livelloCertificato = TREDICI_NC
	function chance = UNA
	function sol = GIUSTA
	function currentDepth = DODICI
	function maxDepth = DODICI
	function control = RICHIESTA_POSIZIONE
	function controlRightWrong = INIZIO_RW
	function posizioneGiusta = INDIETRO
	function rightLimit = DODICI
	function leftLimit = UNO
	function posG = INDIETROG
	function outMessage = CONTINUA
	function reset = RESET_MINMAX