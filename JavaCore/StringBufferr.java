public class StringBufferr {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        System.out.println(sb.capacity());
        StringBuffer sb1 = new StringBuffer("Surya");
        System.out.println(sb1.capacity());

        StringBuffer sb2 = new StringBuffer();
        sb2.append("Surya prakash");
        System.out.println(sb2.capacity());

        StringBuffer sb3 = new StringBuffer();
        sb3.append("Surya Prakash is from Aspire Systems.");
        System.out.println(sb3.capacity());

    }
}
