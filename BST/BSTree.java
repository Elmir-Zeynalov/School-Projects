import java.util.ArrayList;
public class BSTree implements BSTOper {

   private class Node {
    Node left, right;
    // verdier i venstre subtre er < verdien i noden selv
    // verdier i høyre subtre er > verdien i noden selv
    int value;
 
    Node(int v) {
      value=v;
      left=right=null;
    }

    /*
      her kommer en del hejlpe metoder for henting of setting av verdier.
    */
    public int getValue(){
      return value;
    }
    public void setValue(int value){
      this.value=value;
    }
    public void setLeft(Node leftChild){
      left=leftChild;
    }
    public void setRight(Node rightChild){
      right=rightChild;
    }
    public Node leftChild(){
      return left;
    }
    public Node rightChild(){
      return right;
    }
  }


  Node root;
  //konstruktørene for BST
  public BSTree(int rootValue){
    root=new Node(rootValue);
  }
  //konsturt for tomme treet
  public BSTree(){
    root=null;
  }

/*
 * Metoden initialiserer en rekursivt hjelpe metode som finner forelderen til n.
 * @param Node 
 * @return Node 
*/
 private Node findParent( Node n ){
   return parentHelper(root,n);
 }
 /* 
  * Rekursiv hjelpe-metode som går gjennom treet og letter etter forelderen
  * til Noden "target". Måten det skjer er at vi følger en preorder algoritme
  * hvor vi først sjekker om noden er lik en av barna til "parent". Sjekker først venstre og deretter høyre.
  * Hvis ingen av barna til "parent" er lik "target", da sammenligner vi verdien til "target" og parent.
  * og velger en gren basert om target er større eller mindre en parent. Hvis noden ikke finenes 
  * returnerer vi null. 
  *
  * @param Node, Node 
  * @return Node 

 */
 public Node parentHelper(Node parent, Node target){
   if(parent == null){
     return null;
   }

   if(parent.leftChild() != null){
     if(target.getValue() == parent.leftChild().getValue()){
       return parent;
     }
   }

   if(parent.rightChild() != null ){
     if(target.getValue() == parent.rightChild().getValue()){
       return parent;
     }
   }

   if(target.getValue() < parent.getValue()){
     return parentHelper(parent.leftChild(),target);
   }

   if(target.getValue() > parent.getValue()){
     return parentHelper(parent.rightChild(),target);
   }

   return null;
 }
 
 /*
  * Denne metoden bruker FindParent to ganger til å finne forelderen til forelderen til Node n
  * Her tenkte at siden vi bruker en O(log n) algorigte inn i en annen da får vi: 
  
  * @param Node
  * @return Node
 */
 private Node findGrandparent( Node n ){
   return findParent(findParent(n));
 }

 private Node find( int value ){
   return findHelper(root,value);
 }
 /*
  * Hjelpe-metode som går gjennom treet ved å velge en gren hver rekusjon
  * @param Node, int 
  * @return Node
 */
 private Node findHelper(Node node,int value){
   if(node == null){
     return null;
   }else{
     if(value < node.getValue()){
       return findHelper(node.leftChild(),value);
     }else if(value == node.getValue()){
       return node;
     }else{
       // tillfellet hvor : value > node.getValue()
       return findHelper(node.rightChild(),value);
     }
   }
 }

 public void add( int value ){
   if(root != null){
     addNode(root,value);
   }else{
     root=new Node(value);
   }
 }
 private void addNode(Node node, int value){
   if(value < node.getValue()){
     if(node.leftChild() == null){
       node.setLeft(new Node(value));
     }else{
       addNode(node.leftChild(),value);
     }
   }else if(value > node.getValue()){
     if(node.rightChild() == null){
       node.setRight(new Node(value));
     }else{
       addNode(node.rightChild(),value);
     }
   }else{
     //hvis value allerede eksisterer i treet
   }
 }
 public boolean remove( int value ){
   int size_before=treeSize(root);
   int size_after=treeSize(deleteNode(root,value));
   if(size_before == size_after){
     return false;
   }
   return true;
 }
 public Node deleteNode(Node node, int value){
   if(node == null){
     return node;
   }

   if(value < node.getValue()){
     node.setLeft(deleteNode(node.leftChild(),value));
   }else if(value > node.getValue()){
     node.setRight(deleteNode(node.rightChild(),value));
   }else{
     // found node
     if(node.leftChild() == null && node.rightChild() == null){
       node=null;
     }else if(node.leftChild() == null){
       Node temp = node;
       node=node.rightChild();
     }else if(node.rightChild() == null){
       Node temp = node;
       node=node.leftChild();
     }else{
       Node min=findSmallest(node.rightChild());
       node.value=min.value;
       node.setRight(deleteNode(node.rightChild(),min.getValue()));
     }
   }
   return node;
 }

 /*
  * Finner minste elementet i treet og siden vi har en BST, bare går helt til venstre 
  * og stopper når den treffer en null peker(da har vi fant minste elementet). 
  *
  * @paran Node
  * @return Node
 */
 public Node findSmallest(Node n){
   while(n.leftChild() != null){
     n=n.leftChild();
   }
   return n;
 }
 // initialiserer treeSize med root-noden.
 public int size(){
   return treeSize(root);
 }

 /*
  * Teller antall noder på venstre grenen og høyre.
  * til slutt legger dem sammen.
  * den må gå gjennom hele treet.
  *
  * @param Node
  * @return int
 */
 public int treeSize(Node node){
   if(node == null){
     return 0;
   }
   int left =+ treeSize(node.leftChild());
   int right =+ treeSize(node.rightChild());
   return 1+left+right;
 }

 public boolean existsInTree( int value ){
   return find(root,value);
 }
 /*
  * Metoden går gjennom treet ved å velge gren fren til den finner noden med verdien "value"
  * ellers hvis vi ikke finner den da betyr det at "value" mangler og vi returnerer null.
  *
  * @param Node, int
  * @return boolean

 */
 public boolean find(Node node,int value){
   if(node == null){
     return false;
   }else{
     if(value < node.getValue()){
       return find(node.leftChild(),value);
     }else if(node.getValue() == value){
       return true;
     }else{
       return find(node.rightChild(),value);
     }
   }
 }

 /*
  * initialiserer nearest metoden, som finner på den minste verdien som er også nærmest il "value"
  * sender Integer_MAX_Value som initial verdier for closest og diff. Hvis de finnes 
  * noe som er nærmere til "value" da byttes den ut.  
 */
 public int findNearestSmallerThan( int value ){
  int answer = nearest(root, value, Integer.MAX_VALUE, Integer.MAX_VALUE);
  return answer;
 }
 /*
  * Metoden finner nærmeste minste verdi ved å travasere gjennom treete å sjekke om 
  * den besøkte noden er mindre. Hvis ja, da sjekker vi forskjellen. Hvis forskjellen
  * er lik eller mindre (som betyr at verdien er nærmere til verdien "value") da blir
  * den lagret i closestt, og differensen i "diff". Så jeg på måte bevarer selve verdien
  * og dens forkjellen så at metoden kan finne den noden med minste diff og returnere den.
  * 
  * @param Node, int, int, int
  * @return int
 */
 public int nearest(Node n, int value, int closest,int diff){
  if(n.getValue() < value ){
    if(java.lang.Math.abs(value - n.getValue())== diff ){
      closest=n.getValue();
    }else if(java.lang.Math.abs(value - n.getValue()) < diff){
      closest=n.getValue();
      diff=value-n.getValue();
    }else{
     return closest;
    }
  }
  if(n.leftChild() != null){
    closest= nearest(n.leftChild() ,value,closest,diff);
  }
  if(n.rightChild() != null){
    closest= nearest(n.rightChild(),value,closest,diff);
  }
  return closest;
}

/*
 * Enkel metode som tar inn en array og legger alle verdien til treet.
 * @param int[]
 * @return void
*/
 public void addAll( int[] integers ){
   for(int i=0;i<integers.length;i++){
     add(integers[i]);
   }
 }

 /*
  * Iterer gjennom treet og setter verdiene inn i array(i sortert rekkfølge) ved hjelp av
  * inorrder travasering som først besøker vesntre grenen(hvor de mindre elementene havner), 
  * roten og deretter høyre grenen(største elementene).
  * 
  * @param void
  * @return int[]
 */
 public int[] sortedArray(){
   int[] sorted = new int[size()];
   inorder(root,sorted,0);
  return sorted;
 }
/*
 * Inorder travasering metode 
 * 
 * @param Node, in[] , int
 * @return int
 */
 public int inorder(Node node, int[] array,int pos){
   if(node.leftChild() != null){
     pos=inorder(node.leftChild(),array,pos);
   }
   array[pos]=node.getValue();
   pos++;
   if(node.rightChild() != null){
     pos=inorder(node.rightChild(),array,pos);
   }
   return pos;
 }

 /*
  * Metoden fyller en arraylist med verdiene som er mellow "low" og "high" ("low" og "high" er ikke inkludert).
  * array listen fylles ved hjelp av hjelpefunksjonen rangeHelper 
 */
 public int[] findInRange (int low, int high){
  ArrayList<Node> list = new ArrayList<Node> ();
  rangeHelper(root,low,high,list);
  int[] fin=new int[list.size()];
  int i=0;

  for(Node n: list){
    fin[i++]=n.getValue();
  }

  return fin;
 }
 /*
  * Går gjennom hele treet og legger de elementene som er mellom "low" og "high" inn i arrayliste
  * 
  * @param Node, int, int, ArrayList<Node>
  * @return void
 */
 public void rangeHelper(Node node,int low,int high,ArrayList<Node>list){
  if(node == null){
    return;
  }
  if(node.getValue() > low && node.getValue() < high){
    list.add(node);
  }
  if(node.leftChild() != null){
    rangeHelper(node.leftChild(),low,high,list);
  }
  if(node.rightChild() != null){
    rangeHelper(node.rightChild(),low,high,list);
  }
 }


 /*
  * metodene nedover er for testfilen. 
 */

 public int height(){
  return h(root);
 }
 public int h(Node n){
  if(n == null){
    return 0;
  }
  int left;
  if(n.leftChild() != null){
    left = h(n.leftChild())+1;
  }else{
    left = 0;
  }
  int right;
  if(n.rightChild() != null){
    right = h(n.rightChild())+1;
  }else{
    right = 0;
  }

  return Math.max(left,right);
 }

 public int[] preorderArray(){
  int[] array=new int[size()];
  
  preOrder(root,array,0);
  return array;
 }
 public int preOrder(Node node,int[] array,int pos){
  if(node != null){
    array[pos++]=node.getValue();
  }

  if(node.leftChild() != null){
    pos = preOrder(node.leftChild(),array,pos);
  }
  if(node.rightChild() != null){
    pos = preOrder(node.rightChild(),array,pos);
  }
  return pos;
 }


 public int[] postorderArray(){
  int[] array=new int[size()];
  
  postOrder(root,array,0);
  return array;
 }

 public int postOrder(Node node,int[] array,int pos){
  if(node.leftChild() != null){
    pos = postOrder(node.leftChild(),array,pos);
  }
  if(node.rightChild() != null){
    pos = postOrder(node.rightChild(),array,pos);
  }
  if(node != null){
    array[pos++]=node.getValue();
  }
  return pos;
 }
}
