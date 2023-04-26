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
        
        System.out.print("\nプレイヤーの現在の合計ポイントは"+Base.sumPoint(player));
        Base.descriptionSofthand(player); //（もしくは○○）表示
        System.out.println("です。");
        System.out.println("（デバッグ用：ディーラーの現在の合計ポイントは"+Base.sumPoint(dealer)+"です。）");
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

                System.out.print("プレイヤーの現在の手札の内訳は");
                Base.openHand(player);
                System.out.print("合計ポイントは"+Base.sumPoint(player));
                Base.descriptionSofthand(player); //もしくは○○
                System.out.println("です。");
                //バーストチェック
                if(Base.isBursted(Base.sumPoint(player))){
                    System.out.println("\nバーストしてしまいました。あなたの負けです。");
                    System.out.print("ディーラーの手札の内訳は");
                    Base.openHand(dealer);
                    System.out.print("合計ポイントは"+Base.sumPoint(dealer));
                    Base.descriptionSofthand(dealer); 
                    System.out.println("でした。");
                    return;
                }
            }else{
                System.out.println("あなたの入力は"+str+"です。yかnを入力してください。");
            }
        }
        
        //ディーラーのターン
        System.out.println("\nディーラーのターンです。");
        while(true){
            //合計が17以上ならスタンド
            //合計が12～16ならヒット
            //合計が7～11なら　Aが含まれるなら（後から＋10するので）ヒットしない 含まれないならヒット
            //合計が6以下ならヒット
            int sumd = Base.sumPoint(dealer);
            if(17<=sumd){
                System.out.println("（17以上です。スタンド）");
                break;
            }else if(11<sumd && sumd<17){
                System.out.println("（12~16です。ヒット）");
                deckCount = dealer1.addCard(deck,player,dealer,deckCount);
                System.out.println("ディーラーが山札から一枚追加しました。");
                if(Base.isBursted(Base.sumPoint(dealer))){
                    System.out.println("ディーラーがバーストしました。あなたの勝利です。");
                    System.out.print("ディーラーの手札の内訳は");
                    Base.openHand(dealer);
                    System.out.println("合計ポイントは"+Base.sumPoint(dealer)+"でした。");
                    return;
                }
            }else if(7<=sumd && sumd<=11){
                System.out.println("（7~11です。Aが含まれるならスタンド、含まれないならヒット）");
                if(Base.containsA(dealer)){
                    break;
                }else{
                    deckCount = dealer1.addCard(deck,player,dealer,deckCount);
                    System.out.println("ディーラーが山札から一枚追加しました。");
                    if(Base.isBursted(Base.sumPoint(dealer))){
                        System.out.println("ディーラーがバーストしました。あなたの勝利です。");
                        System.out.print("ディーラーの手札の内訳は");
                        Base.openHand(dealer);
                        System.out.println("合計ポイントは"+Base.sumPoint(dealer)+"でした。");
                        return;
                    }
                }
            }else if(sumd<7){
                System.out.println("（6以下です。ヒット）");
                deckCount = dealer1.addCard(deck,player,dealer,deckCount);
                System.out.println("ディーラーが山札から一枚追加しました。");
                if(Base.isBursted(Base.sumPoint(dealer))){
                    System.out.println("ディーラーがバーストしました。あなたの勝利です。");
                    System.out.print("ディーラーの手札の内訳は");
                    Base.openHand(dealer);
                    System.out.println("合計ポイントは"+Base.sumPoint(dealer)+"でした。");
                    return;
                }
            }
        }
        System.out.println("ターン終了\n（デバッグ用）"+dealer);
        
        //ポイント比較
        int playerPoint = Base.sumPoint(player);
        //Aが一つでもあって、合計が11以下なら　Aを11として数え、合計に＋10する。
        if(Base.containsA(player)){
            if(playerPoint<12){
                playerPoint+=10;
            }
        }
        int dealerPoint = Base.sumPoint(dealer);
        if(Base.containsA(dealer)){
            if(dealerPoint<12){
                dealerPoint+=10;
            }
        }
        System.out.print("\r\nあなたの最終ポイントは"+playerPoint+"です。手札の内訳は");
        Base.openHand(player);
        System.out.print("です。\nディーラーの最終ポイントは"+dealerPoint+"です。手札の内訳は");
        Base.openHand(dealer);
        System.out.println("です。");
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
    
    //具象メソッド（全ての子クラスで完全共通の処理。書く必要なし）ポイント計算、合計ポイント表示
    public static int sumPoint(List<Integer> list){
        int sum=0;
        for(int i =0; i<list.size(); i++){
            sum=sum+toPoint(toNumber(list.get(i)));
        }
        return sum;
    }
    //Aの扱いについて　boolean型戻り値でAを含むか・含まないか判定するメソッド　→　含む場合はif文で合計ポイントを＋10
    public static boolean containsA(List<Integer> list){
        boolean hasA;
        if(list.contains(1)||list.contains(14)||list.contains(27)||list.contains(40)){
            return hasA = true;
        }else{
            return hasA= false;
        }
    }
    public static int addTen(List<Integer> list,int sum){
        return sum+10;
    }
    public static void descriptionSofthand(List<Integer> list){
        int sum = sumPoint(list);
        int softsum = addTen(list,sum);
        if(containsA(list)){
            if(sum<=11){
                System.out.print("もしくは"+softsum);
            }
        }
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
    //手札の内容を全て表示
    public static void openHand(List<Integer> list){
        for(int i =0; i <list.size(); i++){
        System.out.print(Base.toDescription(list.get(i))+"、");
        }
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
