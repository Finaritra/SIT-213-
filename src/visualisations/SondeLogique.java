   package visualisations;
	
	
   import information.Information;


/** 
 * Classe réalisant l'affichage d'information composée d'éléments booléens
 * @author prou
 */
   public class SondeLogique extends Sonde <Boolean> {
   
   /** le nombre de pixels en largeur pour un élément d'information Boolean à afficher dans la fenêtre */
      private int nbPixels;
   
   /**
    * pour construire une sonde logique  
    * @param nom  le nom de la fenêtre d'affichage
    * @param nbPixels  le nombre pixels en largeur pour un élément d'information Boolean à afficher dans la fenêtre
    */
      public SondeLogique(String nom, int nbPixels) {
         super(nom);
         this.nbPixels = nbPixels;
      }
   
   
   	 
      public void recevoir (Information <Boolean> information) {  
         if (information.iemeElement(0) instanceof Boolean) {
             int nbElements = information.nbElements();
             boolean [] table = new boolean[nbElements];
             for (int i = 0; i < nbElements; i++) {
                table[i] = information.iemeElement(i);
             }
      
         new VueCourbe (table,  nbPixels, nom); 
      }
      }
   }
   	 
   	
   
   
   