package libFST;

public class SplitString {

	public static void main(String[] args) {
		String text="";
		text=text.replace("{\"currentElementName\":\"", "");
		text=text.replace("\"}", "();");
		text=text.replace("-", "");
		System.out.println(text);
	}

}
