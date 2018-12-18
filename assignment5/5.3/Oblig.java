import java.util.Random;

public class Oblig{

/* Welcome to the class blah blah balh
newline blah blah balh
newline blah balh blah
*/
  public static void main(String[] args){
    int[] arr = {1,7,0,4,9,2,10,15,11,14,-1};
    arr = selectionSort(arr);
    for(int i=0; i<arr.length; i++){
      System.out.print(arr[i] + " ");
    }
    System.out.println();
    System.out.println(arr[i] + "adsf d" + "hello");
    System.out.println(arr[i] + "dfa d");
    System.out.println(arr[i] + "asdf d");


    int[] n = {1,7,0,4,9,2,10,15,11,14,-1};
    arr = n;
    arr = insertionSort(arr);
    for(int i=0; i<arr.length; i++){
      System.out.print(arr[i] + " ");
    }
    System.out.println();

    int[] w = {1,7,0,4,9,-2,10,15,11,14,-1};
    arr = w;
    inPlaceQuickSort(arr,0,w.length-1);
    for(int i=0; i<arr.length; i++){
      System.out.print(arr[i] + " d");
    }
    System.out.println("\n");
    System.out.println(+ w[0] + "Lol");

  }
  // 1. Selection sort
  public static int[] selectionSort(int[] A){
    for(int i =0;i<A.length-1; i++){
      int s =i;
      for(int j =i+1; j<A.length; j++){
        if(A[j] < A[s]){
          s = j;
        }
      }
      if(i != s){
        //swap A[i] and A[min];
        int t = A[s];
        A[s] = A[i];
        A[i] = t;
      }
    }
    return A;
  }

  // 2. Insertion Sort
  public static int[] insertionSort(int[] A){
    for(int i = 1; i<A.length;i++){
      int x = A[i];
      int j = i;
      while(j > 0 && x < A[j-1]){
        A[j] = A[j-1];
        j--;
      }
      A[j] = x;
    }
    return A;
  }

  // 3. Quick Sort
  public static void inPlaceQuickSort(int[] A,int a ,int b){
    if(a>= b){
      return;
    }
    int l = inPlacePartition(A,a,b);
    inPlaceQuickSort(A,a,l-1);
    inPlaceQuickSort(A,l+1,b);
  }
  public static int inPlacePartition(int[] A,int a, int b){
    int r = random.nextInt(b - a + 1) + a;
    int temp = A[r];

    A[r] = A[b];
    A[b] = temp;

    int p =A[b];
    int l = a;
    r = b-1;
    while(l <= r){
      while(l <= r && A[l]<= p){
        l = l + 1;
      }
      while(r >= l && A[r] >= p){
        r--;
      }
      if(l < r){
        temp = A[l];
        A[l] = A[r];
        A[r] = temp;
      }
    }
    temp = A[b];
    A[b] = A[l];
    A[l] = temp;
    return l;
  }

}
