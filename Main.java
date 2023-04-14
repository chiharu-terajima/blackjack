import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("ゲームを開始します。");
        //事前準備　三種のオブジェクト作成、deck作成、player作成＆追加＆ポイント表示、dealer作成＆追加＆ポイント表示
        Deck Deck1 = new Deck();
        Player player1 = new Player();
        Dealer dealer1 = new Dealer();
        
        List<Integer> deck = Deck.makeDeck();
        List<Integer> player = player1.makePlayer();
        List<Integer> dealer = dealer1.makeDealer();
        
        int deckCount = 0;
        int playerHands = 0;
        deckCount = player1.addCard(deck,player,dealer,deckCount);
        System.out.print("あなたの一枚目のカードは"+Base.toDescription(deck.get(deckCount-1))+"です。");
        deckCount = dealer1.addCard(deck,player,dealer,deckCount);
        System.out.println("ディーラーの一枚目のカードは"+Base.toDescription(deck.get(deckCount-1))+"です。");
        deckCount = player1.addCard(deck,player,dealer,deckCount);
        System.out.print("あなたの二枚目のカードは"+Base.toDescription(deck.get(deckCount-1))+"です。");
        deckCount = dealer1.addCard(deck,player,dealer,deckCount);
        System.out.println("ディーラーの二枚目のカードは秘密です。");
        System.out.println("\nプレイヤーの現在の合計ポイントは"+Base.sumPoint(player)+"です。");
        System.out.println("（デバッグ用）ディーラーの現在の合計ポイントは"+Base.sumPoint(dealer)+"です。");
        System.out.println(player);
        System.out.println(dealer);
        
        //動作    　playerがnoを選択するまでカードをひく、ディーラーのポイントが17を超えるまでカードをひく
        System.out.println("\nあなたのターンです。");
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("\nカードを引きますか？Yes:y or No:n");
            //入力読み取り
            String str = scan.next();
            System.out.println("入力された文字は"+str);
            //if(y) カード追加、追加したカードの内容を表示、合計ポイントの表示、バーストチェック
            if("n".equals(str)){
                System.out.println("あなたのターンを終了します。");
                break;
            }else if("y".equals(str)){
                deckCount = player1.addCard(deck,player,dealer,deckCount);
                System.out.println(player);
                System.out.println("あなたが引いたカードは"+Base.toDescription(deck.get(deckCount-1))+"です。");
                System.out.println("プレイヤーの現在の合計ポイントは"+Base.sumPoint(player)+"です。");
                //バーストチェック
                if(Base.isBursted(Base.sumPoint(player))){
                    System.out.println("バーストしてしまいました。あなたの負けです。ディーラーのポイントは"+Base.sumPoint(dealer)+"でした。");
                    return;
                }
            }else{
                System.out.println("あなたの入力は"+str+"です。yかnを入力してください。");
            }
        }
        System.out.println("プレイヤーの現在の合計ポイントは"+Base.sumPoint(player)+"です。");
        
        //ディーラーのターン
        System.out.println("\nディーラーのターンです。");
        while(true){
            if(Base.sumPoint(dealer) < 17){
                deckCount = dealer1.addCard(deck,player,dealer,deckCount);
                System.out.println("ディーラーが山札から一枚追加しました。");
                if(Base.isBursted(Base.sumPoint(dealer))){
                    System.out.println("ディーラーがバーストしました。あなたの勝利です。");
                    return;
                }
            }else{
                System.out.println("ターン終了");
                break;
            }
        }
        System.out.println("（デバッグ用）"+dealer);
        
        //ポイント比較
        int playerPoint = Base.sumPoint(player);
        int dealerPoint = Base.sumPoint(dealer);
        System.out.println("\r\nあなたの最終ポイントは"+playerPoint+"です。");
        System.out.println("ディーラーの最終ポイントは"+dealerPoint+"です。");
        if(playerPoint > dealerPoint){
            System.out.println("あなたの勝利です！");
        }else if(playerPoint == dealerPoint){
            System.out.println("引き分けです。");
        }else{
            System.out.println("あなたの負けです…。");
        }
        
    }
}

//山札のクラス
class Deck {
    //抽象クラスで定義できないもの
    public static List<Integer> makeDeck(){
        //空のリスト作成
        List <Integer> deck = new ArrayList<>(52);        
        //山札に52枚加える
        for(int i= 1; i <= 52; i++){
                deck.add(i);
        }
        //山札をシャッフルする
        Collections.shuffle(deck);
        //デバッグ用　全表示
        System.out.println(deck);
        return deck;
    }
}

//親クラス
abstract class Base {
    //抽象メソッド（クラス毎に処理内容が異なるメソッド）Deck,Player,Dealerオブジェクトに共通の処理　カード追加
    abstract public int addCard(List<Integer> deck,List<Integer> player,List<Integer> dealer,int deckCount);
    //ランクとスートに変換　表示する場合に追加で必要になる処理
    /*abstract public void toRank
    abstract public void toSuit
    abstract public void toDescription
    //合計ポイントの（計算と）表示
    abstract public int sumPoint(List<Integer> player,List<Integer> dealer);*/
    
    //具象メソッド（全ての子クラスで完全共通の処理。書く必要なし）ポイント計算、合計ポイント表示
    public static int sumPoint(List<Integer> list){
        int sum=0;
        for(int i =0; i<list.size(); i++){
            sum=sum+toPoint(toNumber(list.get(i)));
        }
        return sum;
    }
    private static int toNumber(int cardNumber){
        int number = cardNumber % 13;
        if(number==0){
            number = 13;
        }
        return number;
    }
    private static int toPoint(int number){
        if(number==11||number==12||number==13){
            number=10;
        }
        return number;
    }    
    //カードをランクとスートに変換する
    public static String toDescription(int cardNumber){
        String rank = toRank(toNumber(cardNumber));
        String suit = toSuit(cardNumber);
        return suit+"の"+rank;
    }
    private static String toRank(int number){
        switch(number){
        case 1:
            return "A";
        case 11:
            return "J";
        case 12:
            return "Q";
        case 13:
            return "K";
        default:
            String str = String.valueOf(number);
            return str;
        }
    }
    private static String toSuit(int cardNumber){
        switch((cardNumber - 1)/13){
        case 0:
            return "クラブ";
        case 1:
            return "ダイヤ";
        case 2:
            return "ハート";
        case 3:
            return "スペード";
        default:
            return "例外です";
        }
    }
    //バーストチェック
    public static boolean isBursted(int point){
        if(point <= 21){
            return false;
        }else{
            return true;
        }
    }
}    


//子クラス・・・プレイヤーの手札を表すクラスと、それによって実際に組み立てられたオブジェクト
class Player extends Base{
    //フィールド　プレイヤーの手札を作成
    public static List<Integer> makePlayer(){
        List <Integer> player = new ArrayList<>(52);
        return player;
    }
    //継承した抽象メソッドその一　カードの追加
    public int addCard(List<Integer> deck,List<Integer> player,List<Integer> dealer,int deckCount){
        //手札に二枚加える
        player.add(deck.get(deckCount));
        deckCount++;
        return deckCount;
    }        
}





//子クラス・・・プレイヤーの手札を表すクラスと、それによって実際に組み立てられたオブジェクト
class Dealer extends Base{
    //フィールド　プレイヤーの手札を作成
    public static List<Integer> makeDealer(){
        List <Integer> dealer = new ArrayList<>(52);
        return dealer;
    }
    //継承した抽象メソッドその一　カードの追加
    public int addCard(List<Integer> deck,List<Integer> player,List<Integer> dealer,int deckCount){
        //手札に二枚加える
        dealer.add(deck.get(deckCount));
        deckCount++;
        return deckCount;
    }        
}
