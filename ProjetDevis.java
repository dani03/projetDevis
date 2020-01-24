import java.net.Proxy;

public class ProjetDevis {
    public static void displayMenu(){
        Terminal.ecrireStringln("1 : ajouter une ligne ");
        Terminal.ecrireStringln("2 : supprimer une ligne ");
        Terminal.ecrireStringln("3 : ajouter une nouvelle section ");
        Terminal.ecrireStringln("4 : ouvrir une section existante ");
        Terminal.ecrireStringln("5 : afficher un apercu du devis ");
        Terminal.ecrireStringln("6 : quitter et editer ");
        Terminal.ecrireStringln("7 : quitter sans editer ");
        Terminal.ecrireString("votre choix: ");
    }
    public static int afficheTabString(String[] array){
        int nbCaseOccupe =0;
        for (int i = 0; i < array.length; i++){
            //on affiche les produits pour permettre a l'utilisateur de faire un choix de suppression
            if(array[i] != null){
                nbCaseOccupe +=1;
                Terminal.ecrireStringln((i+1)+": "+array[i]);
            }
        }
        return nbCaseOccupe;
    }
    public static String affichageTitre(String prod ){
        String price = prod+"";
        int tailleMax = 20;
        int tailleProd = price.length();
        String espace =" ";
        for (int i = 1; i <= tailleMax - tailleProd; i++){
            espace = espace + " ";
        }
        return price+espace;
    }
    public static String affichageTitre(double prod ){
        String price = prod+"";
        int tailleMax = 11;
        int tailleProd = price.length();
        String espace = " ";
        for (int i = 1; i <= tailleMax - tailleProd; i++){
            espace += " ";
        }
        return espace+price;
    }
    public static void makeDouble(Double[][] prix, String[] tableau){
        for (int i = 0; i < tableau.length; i++){
            for (int j = 0; j < prix[i].length; j++){
                    prix[i][j] = Double.MIN_VALUE;
            }
        }
    }

    public static int catchUserChoice(){
        boolean error;
        int choice = Integer.MIN_VALUE;
        do{
            try{
                error = false;
                displayMenu();
                choice = Terminal.lireInt();
                if (choice <= 0 || choice >7){
                    throw new ArrayIndexOutOfBoundsException();
                }

            }catch (TerminalException e){
                error = true;
                Terminal.ecrireStringln("vous devez entrez un nombre ");

            } catch (ArrayIndexOutOfBoundsException e){
                error = true;
                Terminal.ecrireStringln("vous etes hors des bornes!");
            }
        }while(error);

        return choice;
    }
    public static double getExecptionPrix(){
        boolean itIsNumber;
        double prix= 0.0;
        do {
            try{
                itIsNumber = true;
                 prix = Terminal.lireDouble();
                 if (prix <= 0) throw new NumberFormatException();

            }catch (TerminalException e){
                itIsNumber = false;
                Terminal.ecrireStringln("vous devez entrer un nombre");
                Terminal.ecrireString("entrez un prix: ");
            }catch (NumberFormatException e){
                itIsNumber = false;
                Terminal.ecrireStringln("vous devez entrer nombre positif non nul");
                Terminal.ecrireString("entrez un prix: ");
            }
        }while(!itIsNumber);

        return prix;
    }
    public static Double calculPrixNet(Double[][] prix, int indSection){
        Double somme = 0.0;
        for (int i = 0; i < prix[indSection].length; i++){
            if (prix[indSection][i] != Double.MIN_VALUE){
                somme += prix[indSection][i];
            }
        }
        return somme;
    }

    public static int entrerSectionPremiere( String[] tabSection,String[][] intituAdd, Double[][] prixAdd ){
                int choixUser;
                Terminal.ecrireString("entrez une premiere section: ");
                String section = Terminal.lireString();
                section = controleString(section);
                Terminal.ecrireString("ajouter un produit/intitulé?: ");
                String produit = Terminal.lireString();
                produit = controleString(produit);
                Terminal.ecrireString("entrez le prix: ");
                double price = getExecptionPrix();
                int indice = 0;
                //on regarde si une case est vide et ensuite on insert le domaine
                while( indice < tabSection.length && tabSection[indice] != null){
                    indice ++;
                }
                //on va chercher a regarder la premiere case vide pour inserer les produit du domaine
                int sousIndice =0;
                while (sousIndice < intituAdd[indice].length && intituAdd[indice][sousIndice] != null ){
                    sousIndice++;
                }
                if(indice == tabSection.length){
                    Terminal.ecrireStringln("imposible table pleine");
                    return 404;
                }else {
                    tabSection[indice] = section;
                    intituAdd[indice][sousIndice] = produit;
                    prixAdd[indice][sousIndice] = price;
                    Terminal.ecrireStringln("ajouté avec success!!");
                    Terminal.sautDeLigne();
                    choixUser = catchUserChoice();
                }
        return choixUser;
    }
    //fonction pour savoir si on est dans les bornes
    public static int getIndice(int taille){
        boolean horsIndex;
        Terminal.ecrireString("entrez le numero de section: ");
        int indSection = Integer.MIN_VALUE;
        int nbErreur =0;
        do {
            try{
                horsIndex = false;
                 indSection = Terminal.lireInt();
                 indSection -= 1;
                if( indSection >= taille || indSection <=-1){
                    throw new ArrayIndexOutOfBoundsException();
                }

            }catch (ArrayIndexOutOfBoundsException e){
                nbErreur += 1;
                horsIndex = true;
                Terminal.ecrireStringln("numero de section invalide...");
                if (nbErreur >= 5){
                    Terminal.ecrireStringln("vous n'arrivez pas trouver un indice le programme va etre arreter");
                    indSection = 404;
                    return indSection;
                }
                Terminal.ecrireString("entrez un n° de section valide: ");

            }catch (TerminalException e){
                nbErreur += 1;
                horsIndex = true;
                Terminal.ecrireStringln("vous devez entrez un nombre");
                Terminal.ecrireString("entrez le n° de section modifié: ");
            }
        }while(horsIndex && nbErreur <= 5);

       return indSection;
    }
    public static String controleString(String mot){
        mot = mot.trim();
        boolean errorNumber;
        do {
            try {
                errorNumber = true;
                Integer.parseInt(mot);
                Terminal.ecrireString("vous ne pouvez pas entrez des nombres comme nom reessayez: ");
                mot = Terminal.lireString();
            }catch(NumberFormatException e){
                errorNumber = false;
            }
        }while(errorNumber);

        while (mot.isEmpty()){
            Terminal.ecrireString("le champ ne peut etre vide entrez un nom: ");
            mot = Terminal.lireString();
        }
        return mot;
    }
    public static void ajoutElement(String[] TableauSection,String[][] TabProduit, Double[][] TabPrix){
        try {
            int indice = 0;
            Terminal.ecrireString("ajouter un produit a la section courante: ");
            String produitAjoute = Terminal.lireString();
            produitAjoute = controleString(produitAjoute);
            Terminal.ecrireString("entrez un prix: ");
            double prixProduit = getExecptionPrix();
            while( indice < TableauSection.length && TableauSection[indice] != null){
                indice ++;
            }
            //on va chercher a regarder la premiere case vide pour inserer les produits du domaine
            int sousIndice = 0;
            while (sousIndice < TabProduit[indice].length && TabProduit[indice -1][sousIndice] != null ){
                sousIndice++;
            }
            TabProduit[indice -1][sousIndice] = produitAjoute;
            TabPrix[indice -1][sousIndice] = prixProduit;
            Terminal.ecrireStringln("produit ajouté avec success!!");
            Terminal.sautDeLigne();
        }catch (ArrayIndexOutOfBoundsException e){
            Terminal.ecrireStringln("vous devez au moins rentrez une section ..");
            entrerSectionPremiere(TableauSection,TabProduit, TabPrix);
        }

    }
    public static void entrerSectionContinue( String[] tabSection,String[][] intituAdd, Double[][] prixAdd){

        Terminal.sautDeLigne();
        Terminal.ecrireString("entrez une nouvelle section: ");
        String domaine = Terminal.lireString();
        domaine = controleString(domaine);
        Terminal.ecrireString("ajouter un produit/intitulé?: ");
        String produit = Terminal.lireString();
        produit = controleString(produit);
        Terminal.ecrireString("entrez le prix: ");
        double price = getExecptionPrix();
        int indice = 0;
        while( indice < tabSection.length && tabSection[indice] != null){
            indice ++;
        }
        //on va chercher a regarder la premiere case vide pour inserer les produit du domaine
        int sousIndice =0;
        while (sousIndice < intituAdd[sousIndice].length && intituAdd[indice][sousIndice] != null ){
            sousIndice++;
        }
        tabSection[indice] = domaine;
        intituAdd[indice][sousIndice] = produit;
        prixAdd[indice][sousIndice] = price;
        Terminal.ecrireStringln("ajouté avec success!!");
        Terminal.sautDeLigne();
    }
    public static void openSectionExist(String[] tabSection,String[][] intituAdd, Double[][] prixAdd){
        int taille = afficheTabString(tabSection);

        int indSection = getIndice(taille);

        int indice = 0;
        Terminal.ecrireString("ajouter un produit a la section: ");
        String produitAjoute = Terminal.lireString();
        produitAjoute = controleString(produitAjoute);
        Terminal.ecrireString("entrez un prix: ");
        double prixProduit = getExecptionPrix();
        while( indice < tabSection.length && ! tabSection[indice].equals(tabSection[indSection])){
            indice ++;
        }
        //on va chercher a regarder la premiere case vide pour inserer les produit du domaine
        int sousIndice = 0;
        while (sousIndice < intituAdd[indSection].length && intituAdd[indSection][sousIndice] != null ){
            sousIndice++;
        }
        intituAdd[indSection][sousIndice] = produitAjoute;
        prixAdd[indSection][sousIndice] = prixProduit;
        Terminal.ecrireStringln("produit ajouté avec success!!");
        Terminal.sautDeLigne();
    }
    public static void procedeSuppression(String[] tableauSection,String[][] tableauIntitule, Double[][] prixAdd){
        int taille = afficheTabString(tableauSection);
        int indiceSection = getIndice(taille);
        if (indiceSection == 404) return;

        for (int i = 0; i < tableauIntitule[i].length; i++){
            if(tableauIntitule[indiceSection][i] != null){
                Terminal.ecrireStringln((i+1)+": "+tableauIntitule[indiceSection][i]);
            }
        }
        int caseOccupe = 0;
        boolean horsIndex;
        do {
            try {
                Terminal.ecrireString("quel produit voulez vous retirez: ");
                int indiceTab2 = Terminal.lireInt();
                indiceTab2 -= 1;
                while(indiceTab2 > 0 && tableauIntitule[indiceSection][indiceTab2] == null){

                    Terminal.ecrireStringln("nombre invalide reessayer...");
                    Terminal.ecrireString("quel produit voulez vous retirez: ");
                    indiceTab2 = Terminal.lireInt();
                    indiceTab2 -= 1;
                }

                while(caseOccupe < tableauIntitule[indiceSection].length && ! tableauIntitule[indiceSection][caseOccupe].equals(tableauIntitule[indiceSection][indiceTab2])){
                    caseOccupe += 1;
                }
                horsIndex = false;
            }catch (ArrayIndexOutOfBoundsException e){
                horsIndex = true;
                Terminal.ecrireStringln(" ce numero n'est pas valide ");

            }catch (TerminalException ex){
                horsIndex = true;
                Terminal.ecrireStringln(" les lettres ne sont pas valide reessayez ");
            }
        }while(horsIndex);

        if (caseOccupe == tableauIntitule.length){
            Terminal.ecrireStringln("la case n'a pas ete trouvé");
        }else{
            //ici on appelle la fonction suppression afin d'eviter les trous et supprimer la valeur
            suppression(caseOccupe,indiceSection, tableauIntitule, prixAdd, tableauSection);
            //ici on va verifier si toute les cases sont vide si oui on supprime aussi la section
            Terminal.ecrireStringln(" l'element a bien été supprimé!!");
        }
    }
    public static void afficherDevis(String[] section, String[][] produit, Double[][] prixProduit){

        Double sousTotal = 0.0;
        double total = 0.0;

        int ptCase;
        for (int i = 0; i < section.length; i++){
            if (section[i] != null){
                Terminal.sautDeLigne();
                Terminal.ecrireStringln(section[i].toUpperCase()+":");
            }
            for(ptCase = 0; ptCase < produit[i].length; ptCase++){
                if (section[i] != null && produit[i][ptCase] != null && (prixProduit[i][ptCase] != Double.MIN_VALUE)){
                    Terminal.ecrireStringln("       "+affichageTitre(produit[i][ptCase].toUpperCase())+prixProduit[i][ptCase]+" €");
                    sousTotal = calculPrixNet(prixProduit, i);
                }
            }
            if (section[i] != null || prixProduit[i][ptCase -1] != Double.MIN_VALUE ){
                Terminal.sautDeLigne();
                Terminal.ecrireStringln(affichageTitre("       sous total:".toUpperCase())+affichageTitre(sousTotal)+" €");
                total += sousTotal;
            }
        }
        Terminal.ecrireStringln("_________________________________________");
        Terminal.ecrireStringln(affichageTitre("       net a payer:".toUpperCase())+affichageTitre(total)+" €");
        Terminal.sautDeLigne();
    }
    public static void suppression(int casePrise,int indice, String[][] sousTab, Double[][] tabPrix, String[] section){
        for (int i = casePrise; i < sousTab[indice].length - 1; i++){
            sousTab[indice][i] = sousTab[indice][i + 1];
            sousTab[indice][i + 1] = null;
            tabPrix[indice][i] = tabPrix[indice][i + 1];
            tabPrix[indice][i + 1] = Double.MIN_VALUE;
        }
        boolean vide = false;
        int i;
        for ( i = 0; i < sousTab[indice].length -1; i++){
            if(sousTab[indice][i] != null){
                vide = false;
                break;
            }else if(sousTab[indice][i] == null){
                vide= true;
            }
        }
        if(vide){
            section[indice] = null;
            Terminal.ecrireString(" plus aucun produit SECTION SUPPRIMÉ...");
        }
    }
    public static int traitement(int userChoice, String[] tabSection,String[][] intituAdd, Double[][] prixAdd){
        do {
            if (userChoice == 3){
                entrerSectionContinue(tabSection,intituAdd,prixAdd);
            }else if (userChoice == 5){
                afficherDevis(tabSection,intituAdd,prixAdd);
            }
            else if (userChoice == 1){ // ajouter une ligne
              ajoutElement(tabSection,intituAdd,prixAdd);
            }
            else if(userChoice == 2){//retirer un produit
                procedeSuppression(tabSection,intituAdd,prixAdd);
            }
            else if(userChoice == 4){ //ouvrir une secton exsistante
                openSectionExist(tabSection,intituAdd,prixAdd);
            }

            userChoice = catchUserChoice();

        }while(userChoice != 6 && userChoice != 7);

        return userChoice;
    }

    public static void main(String[] args){
        String[] tabSections = new String[50];
        String[][] intituleAdd = new String[tabSections.length][20];
        Double[][] prixAdd = new Double[tabSections.length][intituleAdd.length];
        makeDouble(prixAdd,tabSections);
        int choixFinal =0;
        int user = entrerSectionPremiere(tabSections, intituleAdd, prixAdd);
        if(user < 6) choixFinal = traitement(user,tabSections,intituleAdd,prixAdd);

        if(user == 7 || choixFinal == 7) Terminal.ecrireStringln("rien à faire. aurevoir...");

        if(user == 6 || choixFinal == 6) afficherDevis(tabSections,intituleAdd,prixAdd);

    }
}
