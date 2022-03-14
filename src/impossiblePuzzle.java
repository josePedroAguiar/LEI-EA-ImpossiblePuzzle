import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.StringTokenizer;
/*
Jose Pedro Silva Aguiar (2019224624)
Filipe (2019224624)
*/

/*
NOTA:
  ??tens de ter para 2+n*2+i - tens de ter 4+(4n)*i numeros iguais???
    hashmap/hashtree- positions de elementos de comparacao
*/
public class impossiblePuzzle {

    static public class Rotation {
        ArrayList<Integer> numbers=new ArrayList<>();
        public Rotation(ArrayList<Integer> numbers){
            this.numbers = numbers;

        }
        public Rotation(){

        }

    }

    static public class Card {
        ArrayList<Rotation> rotations=new ArrayList<>();
        int used=0;
        public Card(ArrayList<Rotation> rotation){

            this.rotations = rotation;
        }
        public Card(){

        }

    }

    public static ArrayList<Integer> makePositions ( ArrayList<Integer> card)
    {
        ArrayList<Integer> aux=new ArrayList<>(card);
        aux.add(0, aux.get(card.size() - 1));
        aux.remove(aux.size()-1);
        return aux ;
    }
    public static boolean solving( ArrayList<Card> allCards,int nColuna,int nCards,ArrayList<Rotation> board,Hashtable<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> hm,Hashtable<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> hm2,int i){

        Card position = allCards.get(i);
        int k=0;
        while (k< 4){
            if (i==0){
                board.set(i,position.rotations.get(k));
                if (i == nCards){
                    return true;
                }

                if (solving_1(allCards,nColuna,nCards,board,hm,hm2,i+1) == true)
                    return true;
            }
            k++;
        }
        return false;
    }
    public static boolean solving_1( ArrayList<Card> allCards,int nColuna,int nCards,ArrayList<Rotation> board, Hashtable<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> hm,Hashtable<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> hm2,int i){
        ArrayList<Integer>aux_par= new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> item=null;

        /*
        for (Rotation r : board)
            System.out.print(r.numbers);
        System.out.println();
         */
        if (i >= nColuna) {
        if(i%nColuna!=0){

                aux_par.add(board.get(i-1).numbers.get(1));
                aux_par.add(board.get(i-1).numbers.get(2));

            if((item=hm.get(aux_par))==null){
                return false;}
        }
        else {
            ArrayList<Integer> a = new ArrayList<>();
            aux_par.add(board.get(i-nColuna).numbers.get(3));
            aux_par.add(board.get(i-nColuna).numbers.get(2));
            if((item=hm2.get(aux_par))==null){
                return false;}
        }

        }
        else {
            if(i%nColuna!=0){
            aux_par.add(board.get(i-1).numbers.get(1));
            aux_par.add(board.get(i-1).numbers.get(2));
                if((item=hm.get(aux_par))==null){
                    return false;}
            }
             else {
                ArrayList<Integer> a = new ArrayList<>();
                aux_par.add(board.get(i-nColuna).numbers.get(3));
                aux_par.add(board.get(i-nColuna).numbers.get(2));
                if((item=hm2.get(aux_par))==null){
                    return false;}
                }



        }
        if (item==null)
            return false;
        for (int n=0;n<item.size();n++) {
            Card position = allCards.get(item.get(n).get(0));
            if(position.used==1){
                continue;
            }


            int k=item.get(n).get(1);
                if (i >= nColuna) {

                    //esta noutra linha logo tens de fazer a verificacao do elementos das cartas (CardnoBoard pos 2 == pos 0 e CardnoBoard Fpos 3 == pos 1)
                    // e comparar com a carta anterior (CardnoBoard pos 1 == pos 0 | CardnoBoard pos 3 == pos 2)
                    if (i % nColuna == 0) {

                                if (position.rotations.get(k).numbers.get(0) == board.get(i-nColuna).numbers.get(3) && position.rotations.get(k).numbers.get(1) == board.get(i-nColuna).numbers.get(2)) {
                                    board.set(i, position.rotations.get(k));
                                    //Collections.swap(item,n,item.size()-1);
                                    position.used=1;
                                    if (i == nCards - 1) {
                                        return true;
                                    }

                                    if (solving_1(allCards, nColuna, nCards, board,hm ,hm2,i + 1) == true) {

                                        return true;
                                    }


                        }
                    } else {
                            if (position.rotations.get(k).numbers.get(0) == board.get(i - 1).numbers.get(1) && position.rotations.get(k).numbers.get(3) == board.get(i - 1).numbers.get(2)) {
                                if (position.rotations.get(k).numbers.get(0) == board.get(i-nColuna).numbers.get(3) && position.rotations.get(k).numbers.get(1) == board.get(i-nColuna).numbers.get(2)) {
                                    board.set(i, position.rotations.get(k));
                                    //Collections.swap(item,n,item.size()-1);
                                    position.used=1;
                                    if (i == nCards - 1) {
                                        return true;
                                    }

                                    if (solving_1(allCards, nColuna, nCards, board,hm ,hm2,i + 1) == true) {
                                        return true;

                                    }
                                }
                            }

                    }
                } else {
                    // comparar com a carta anterior (CardnoBoard pos 1 == pos 0 | CardnoBoard pos 3 == pos 2)
                    if (position.rotations.get(k).numbers.get(0) == board.get(i - 1).numbers.get(1) && position.rotations.get(k).numbers.get(3) == board.get(i - 1).numbers.get(2)) {
                        board.set(i, position.rotations.get(k));
                        //Collections.swap(item,n,item.size()-1);
                        position.used=1;
                        if (i == nCards - 1) {
                            return true;
                        }
                        if (solving_1(allCards, nColuna, nCards, board,hm ,hm2,i + 1) == true) {
                            return true;
                        }
                    }


            }
            position.used=0;
        }
        return false;
    }



    public static void main ( String[] args ) {
        impossiblePuzzle.InputReader in = new impossiblePuzzle.InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<Rotation> board = new ArrayList<>();
        ArrayList<Card> allCards;
        ArrayList<ArrayList<Integer>> position = new ArrayList<>();
        int number1 = in.nextInt();

        for (int i = 0; i < number1; i++) {
            board = new ArrayList<>();
            int nCards = in.nextInt();
            int line = in.nextInt();
            int column = in.nextInt();
            for (int j = 0; j < line; j++) {
                for (int k = 0; k < column; k++) {
                    Rotation card = new Rotation();
                    board.add(card);
                }
            }
            Hashtable<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> hm = new Hashtable<ArrayList<Integer>, ArrayList<ArrayList<Integer>>>();
            Hashtable<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> hm2 = new Hashtable<ArrayList<Integer>, ArrayList<ArrayList<Integer>>>();
            Hashtable<Integer, ArrayList<Integer>> occ_number = new Hashtable<Integer, ArrayList<Integer>>();
            allCards = new ArrayList<Card>();
            ArrayList<Integer> aux_par;
            ArrayList<ArrayList<Integer>> item;
            int par_count = 0;
            int square_count = 0;
            int count = 0;
            for (int j = 0; j < nCards; j++) {
                Card card = new Card();
                ArrayList<Integer> numbers = new ArrayList<>();
                //int[]numbers;
                ArrayList<Integer> occ = null;
                for (int k = 0; k < 4; k++) {
                    int a = in.nextInt();

                    numbers.add(a);
                    if ((occ = occ_number.get(a)) == null) {
                        count++;
                        occ = new ArrayList<>();
                        occ.add(1);
                        occ.add(j);
                        occ_number.put(a, occ);
                        occ = null;
                    } else {
                            int aux = occ.get(0) + 1;
                            occ.remove(0);
                            occ.remove(0);
                            occ.add(0, aux);
                            occ.add(1, j);
                            occ_number.put(a, occ);

                            if (occ.get(0) == 2) {
                                count--;
                            }
                            if (occ.get(0) % 2 == 0) {
                                //System.out.println(i+" -> "+a);
                                par_count++;
                            }
                            if (occ.get(0) % 4 == 0) {
                                square_count++;
                            }

                    }


                }
                ArrayList<ArrayList<Integer>> ocurrencias = new ArrayList<>();
                Rotation rotation = new Rotation(numbers);
                //
                aux_par = new ArrayList<Integer>();
                card.rotations.add(rotation);
                aux_par.add(rotation.numbers.get(0));
                aux_par.add(rotation.numbers.get(3));
                ArrayList<Integer> ocurrencia = new ArrayList<Integer>();

                ocurrencia.add(j);
                ocurrencia.add(0);
                ocurrencias.add(ocurrencia);
                if ((item = hm.get(aux_par)) == null)
                    hm.put(aux_par, ocurrencias);
                else {
                    item.add(ocurrencia);
                    hm.put(aux_par, item);
                }

                aux_par = new ArrayList<Integer>();
                aux_par.add(rotation.numbers.get(0));
                aux_par.add(rotation.numbers.get(1));
                if ((item = hm2.get(aux_par)) == null)
                    hm2.put(aux_par, ocurrencias);
                else {
                    item.add(ocurrencia);
                    hm2.put(aux_par, item);
                }

                //
                aux_par = new ArrayList<Integer>();
                ocurrencias = new ArrayList<>();
                ocurrencia = new ArrayList<Integer>();


                Rotation aux_p = new Rotation(makePositions(numbers));

                card.rotations.add(aux_p);

                aux_par.add(aux_p.numbers.get(0));
                aux_par.add(aux_p.numbers.get(3));
                ocurrencia.add(j);
                ocurrencia.add(1);
                ocurrencias.add(ocurrencia);

                if ((item = hm.get(aux_par)) == null)
                    hm.put(aux_par, ocurrencias);
                else {
                    item.add(ocurrencia);
                    hm.put(aux_par, item);
                }

                aux_par = new ArrayList<Integer>();
                aux_par.add(aux_p.numbers.get(0));
                aux_par.add(aux_p.numbers.get(1));
                if ((item = hm2.get(aux_par)) == null)
                    hm2.put(aux_par, ocurrencias);
                else {
                    item.add(ocurrencia);
                    hm2.put(aux_par, item);
                }

                //
                aux_par = new ArrayList<Integer>();

                aux_p = new Rotation(makePositions(makePositions(numbers)));
                ocurrencias = new ArrayList<>();
                ocurrencia = new ArrayList<Integer>();

                card.rotations.add(aux_p);

                aux_par.add(aux_p.numbers.get(0));
                aux_par.add(aux_p.numbers.get(3));
                ocurrencia.add(j);
                ocurrencia.add(2);
                ocurrencias.add(ocurrencia);

                if ((item = hm.get(aux_par)) == null)
                    hm.put(aux_par, ocurrencias);
                else {
                    item.add(ocurrencia);
                    hm.put(aux_par, item);
                }


                aux_par = new ArrayList<Integer>();
                aux_par.add(aux_p.numbers.get(0));
                aux_par.add(aux_p.numbers.get(1));
                if ((item = hm2.get(aux_par)) == null)
                    hm2.put(aux_par, ocurrencias);
                else {
                    item.add(ocurrencia);
                    hm2.put(aux_par, item);
                }

                //


                aux_par = new ArrayList<Integer>();
                aux_p = new Rotation(makePositions(makePositions(makePositions(numbers))));
                ocurrencias = new ArrayList<>();
                ocurrencia = new ArrayList<Integer>();

                ocurrencia.add(j);
                ocurrencia.add(3);

                card.rotations.add(aux_p);

                aux_par = new ArrayList<Integer>();
                aux_par.add(aux_p.numbers.get(0));
                aux_par.add(aux_p.numbers.get(3));
                ocurrencias.add(ocurrencia);

                if ((item = hm.get(aux_par)) == null)
                    hm.put(aux_par, ocurrencias);
                else {
                    item.add(ocurrencia);
                    hm.put(aux_par, item);
                }

                aux_par = new ArrayList<Integer>();
                aux_par.add(aux_p.numbers.get(0));
                aux_par.add(aux_p.numbers.get(1));
                if ((item = hm2.get(aux_par)) == null)
                    hm2.put(aux_par, ocurrencias);
                else {
                    item.add(ocurrencia);
                    hm2.put(aux_par, item);
                }
                //
                allCards.add(card);

            }
                /*
            System.out.println(hm);
            System.out.println(hm2);
            for (Card c: allCards){

                for (Rotation r : c.rotations)
                    System.out.print(r.numbers);
                System.out.println();}
*/

            //out.println(count);
            //out.println(par_count);
            if (count < 4 && square_count>=((column-1)*(line-1))&&par_count>=2*nCards-2) {
                if (solving(allCards, column, nCards, board, hm, hm2, 0)) {
                    String str = "";
                    String str1 = "";
                    String str2 = "";
                    String aux = "  ";
                    for (int a = 0; a < board.size(); a++) {
                        if (a != 0 && a % column == 0) {
                            str += str1 + "\n" + str2 + "\n\n";
                            str1 = "";
                            str2 = "";
                        }

                        if ((a + 1) % column == 0) {
                            aux = "";
                        }
                        str1 += board.get(a).numbers.get(0) + " " + board.get(a).numbers.get(1) + aux;
                        str2 += board.get(a).numbers.get(3) + " " + board.get(a).numbers.get(2) + aux;
                        aux = "  ";
                    }


                    str += str1 + "\n" + str2 + "\n";
                    out.print(str);
                } else {
                    out.println("impossible puzzle!");
                }

            }
            else {
                out.println(count);
                out.println(par_count);
                out.println("impossible puzzle!");
            }
        }

        out.close();
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader ( InputStream stream ) {
            reader = new BufferedReader(new InputStreamReader(stream));
            tokenizer = null;
        }

        public String next () {
            while ( tokenizer == null || !tokenizer.hasMoreTokens() ) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt () {
            return Integer.parseInt(next());
        }
    }
}