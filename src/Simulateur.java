
   import sources.*;

   import destinations.*;
   import transmetteurs.*;

   import information.*;

   import visualisations.*;

   import java.util.regex.*;


import java.util.*;
   import java.lang.Math;

	
   import java.io.FileInputStream;
   import java.io.FileNotFoundException;
   import java.io.BufferedWriter;
   import java.io.FileWriter;
   import java.io.IOException;
   import java.io.PrintWriter;
  import java.text.DecimalFormat;

/** La classe Simulateur permet de construire et simuler une chaine de transmission composée d'une Source, d'un nombre variable de Transmetteur(s) et d'une Destination.  
 * @author cousin
 * @author prou
 *
 */
   public class Simulateur {
      	
   /** indique si le Simulateur utilise des sondes d'affichage */
      private          boolean affichage = false;
   /** indique si le Simulateur utilise un message généré de mani�re al�atoire */
      private          boolean messageAleatoire = true;
   /** indique si le Simulateur utilise un germe pour initialiser les g�n�rateurs al�atoires */
      private          boolean aleatoireAvecGerme = false;
   /** la valeur de la semence utilis�e pour les générateurs al�atoires */
      private          Integer seed = null;
   /** la longueur du message al�atoire � transmettre si un message n'est pas impose */
      private          int nbBitsMess = 100; 
   /** la cha�ne de caract�res correspondant � m dans l'argument -mess m */
      private          String messageString = "100";
   	
   
   
   	
   /** le  composant Source de la chaine de transmission */
      private			  Source <Boolean>  source = null;
   /** le  composant Transmetteur parfait logique de la chaine de transmission */
      private			  Transmetteur <Boolean, Boolean>  transmetteurLogique = null;
   /** le  composant Destination de la chaine de transmission */
      private			  Destination <Boolean>  destination = null;
   	
   
   /** Le constructeur de Simulateur construit une cha�ne de transmission compos�e d'une Source Boolean, d'une Destination Boolean et de Transmetteur(s) [voir la m�thode analyseArguments]...  
   * <br> Les diff�rents composants de la cha�ne de transmission (Source, Transmetteur(s), Destination, Sonde(s) de visualisation) sont cr��s et connect�s.
   * @param args le tableau des diff�rents arguments.
   *
   * @throws ArgumentsException si un des arguments est incorrect
   *
   */   
      public  Simulateur(String [] args) throws ArgumentsException {
      
      	// analyser et r�cup�rer les arguments
      	
         analyseArguments(args);
      
       Information<Boolean> infoFixe = new Information<Boolean>();
       if( messageAleatoire == false)
       {
    	   String bit;
    	   for(int i = 0; i<nbBitsMess;i++)
    	   {
    		   bit = messageString.substring(i,i+1);
    		   if(bit.equals("1"))
    		   {
    			   infoFixe.add(true);
    		   }
    		   else if (bit.equals("0"))
    			   infoFixe.add(false);
    	   }
	System.out.println("Message émis: "+messageString);
       }
       else
       {
	   Random randomno;
	   if(aleatoireAvecGerme == true) randomno = new Random(seed);
           else randomno = new Random();
	   messageString = "";
    	   for (int i=0;i<nbBitsMess;i++)  
		{
			if(randomno.nextBoolean() == true)
    	        	  { 
				infoFixe.add(true);
				messageString = messageString + "1";
			  }
			else 
			  {
				infoFixe.add(false);
				messageString = messageString +"0";
			  }
		}
	 System.out.println("Message émis: "+messageString);
       }
  	 
  	   
  	   
  	   SondeLogique sonde1 = new SondeLogique("Source",100);
  	   SondeLogique sonde2 = new SondeLogique("TransmissionLogique",100);
  	   
  	   
  	   /** le  composant Source de la chaine de transmission */
  	      source = new SourceFixe(infoFixe);
  	   /** le  composant Transmetteur parfait logique de la chaine de transmission */
  	      transmetteurLogique = new TransmetteurParfait();
  	      /** le  composant Destination de la chaine de transmission */
  	      destination = new DestinationFinale();
  
	  
  	      /**Connexion des composants**/
	      if(affichage == true)//utilisation des sondes
		{
  	      	source.connecter(sonde1);
			transmetteurLogique.connecter(sonde2);
		}
  	      transmetteurLogique.connecter(destination);
  	      source.connecter(transmetteurLogique);
      		
      }
   
   
   
   /** La méthode analyseArguments extrait d'un tableau de cha�nes de caractères les diff�rentes options de la simulation. 
   * Elle met à jour les attributs du Simulateur.
   *
   * <br>  Les arguments autorisés sont : 
   * <dl>
   * <dt> -mess m  </dt><dd> m (String) constitu� de 7 ou plus digits à 0 | 1, le message à transmettre</dd>
   * <dt> -mess m  </dt><dd> m (int) constitu� de 1 à 6 digits, le nombre de bits du message "aléatoire" à transmettre</dd> 
   * <dt> -s </dt><dd> utilisation des sondes d'affichage</dd>
   * <dt> -seed v </dt><dd> v (int) d'initialisation pour les générateurs aléatoires</dd> 
   * <dt> -form f </dt><dd>  codage (String) RZ, NRZR, NRZT, la forme d'onde du signal à transmettre (RZ par d�faut)</dd>
   * <dt> -nbEch ne </dt><dd> ne (int) le nombre d'échantillons par bit (ne supérieur ou égale 6 pour du RZ, ne supérieur ou égale 9 pour du NRZT, ne supérieur ou égale 18 pour du RZ,  30 par défaut))</dd>
   * <dt> -ampl min max </dt><dd>  min (float) et max (float), les amplitudes min et max du signal analogique à transmettre ( min inférieur à max, 0.0 et 1.0 par défaut))</dd> 
   * 
   * <dt> -snr s </dt><dd> s (float) le rapport signal/bruit en dB</dd>
   * 
   * <dt> -ti i dt ar </dt><dd> i (int) numero du trajet indirect (de 1 à 5), dt (int) valeur du decalage temporel du iéme trajet indirect 
   * en nombre d'échantillons par bit, ar (float) amplitude relative au signal initial du signal ayant effectuè le iéme trajet indirect</dd>
   * 
   * <dt> -transducteur </dt><dd> utilisation de transducteur</dd>
   * 
   * <dt> -aveugle </dt><dd> les r�cepteurs ne connaissent ni l'amplitude min et max du signal, ni les diff�rents trajets indirects (s'il y en a).</dd>
   * 
   * </dl>
   * <b>Contraintes</b> :
   * Il y a des interdépendances sur les paramétres effectifs. 
   * @param args le tableau des différents arguments.
   * @throws ArgumentsException si un des arguments est incorrect.
   *
   */   
      public  void analyseArguments(String[] args)  throws  ArgumentsException {
      		
         for (int i=0;i<args.length;i++){ 
         
              
            if (args[i].matches("-s")){
               affichage = true;
            }
            else if (args[i].matches("-seed")) {
               aleatoireAvecGerme = true;
               i++; 
            	// traiter la valeur associee
               try { 
                  seed =new Integer(args[i]);
               }
                  catch (Exception e) {
                     throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
                  }           		
            }
            
            else if (args[i].matches("-mess")){
               i++; 
            	// traiter la valeur associee
               messageString = args[i];
               if (args[i].matches("[0,1]{7,}")) {
                  messageAleatoire = false;
                  nbBitsMess = args[i].length();
               } 
               else if (args[i].matches("[0-9]{1,6}")) {
                  messageAleatoire = true;
                  nbBitsMess = new Integer(args[i]);
                  if (nbBitsMess < 1) 
                     throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
               }
               else 
                  throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
            }
                                   
            else throw new ArgumentsException("Option invalide :"+ args[i]);
         }
      
      }
     
    
   	
   /** La méthode execute effectue un envoi de message par la source de la cha�ne de transmission du Simulateur. 
   * @throws Exception si un probléme survient lors de l'ex�cution
   */ 
      public void execute() throws Exception {      
    	  	
    	
    	
    	      /** �mission des donn�es*/
    	      source.emettre();
    	      
    	   
  	      
      }
   
   	   	
   	
   /** La méthode qui calcule le taux d'erreur binaire en comparant les bits du message �mis avec ceux du message re�u.
   *
   * @return  La valeur du Taux dErreur Binaire.
   */   	   
      public float  calculTauxErreurBinaire() {
      
    			Information<Boolean> infoSource = source.getInformationEmise();
    			Information<Boolean> infoDestination = destination.getInformationRecue();
    		
    			 
    			float nbErreur = 0;//Initialisation du nombre d'erreur
    			float min = infoDestination.nbElements();
			System.out.println("Nombre de bits: ");
    			System.out.println(infoDestination.nbElements());	
    			
    			if ((infoSource.nbElements() < infoDestination.nbElements())
    					|| (infoSource.nbElements() > infoDestination.nbElements())) {
    				//On regarde le cas ou il y a des bits rajout� ou des bits perdu lors de la transmission

    				nbErreur += Math.abs(infoSource.nbElements() - infoDestination.nbElements());
    				//On ajoute un nombre d'erreur �gal � la diff�rence de taille entre l'information re�u et l'information
    				//envoy�e par la source
    				min = Math.min(infoSource.nbElements(), infoDestination.nbElements());
    				//min prend la valeur de la plus petite des informations
    			}
    			
    			for (int i = 0; i < min; i++) {
    				if (!infoSource.iemeElement(i).equals(infoDestination.iemeElement(i))) {
    					nbErreur++;
    					//On incremente le nombre d'erreur � chaque fois que les bits corespondant ne sont pas �gaux
    				}
    			}

    			 
    			float txErreur = nbErreur / min;
    		    return txErreur;
      	 
      }
   
   
   
   
   /** La fonction main instancie un Simulateur � l'aide des arguments param�tres et affiche le r�sultat de l'ex�cution d'une transmission.
   *  @param args les diff�rents arguments qui serviront � l'instanciation du Simulateur.
   */
      public static void main(String [] args) { 
      
         Simulateur simulateur = null;
      	
         try {
            simulateur = new Simulateur(args);
         }
            catch (Exception e) {
               System.out.println(e); 
               System.exit(-1);
            } 
      		
         try {
            simulateur.execute();
           float tauxErreurBinaire = simulateur.calculTauxErreurBinaire();
            String s = "java  Simulateur  ";
            for (int i = 0; i < args.length; i++) {
         		s += args[i] + "  ";
         	}
            System.out.println(s + "  =>   TEB : " + tauxErreurBinaire);
         }
            catch (Exception e) {
               System.out.println(e);
               e.printStackTrace();
               System.exit(-2);
            }              	
      }
   	
   }

