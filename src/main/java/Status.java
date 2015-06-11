
public enum Status {
 JOINING("Joining"),LOST("Lost");
 
 	private String text;

	Status(String text){
		this.setText(text);
	 
 }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
