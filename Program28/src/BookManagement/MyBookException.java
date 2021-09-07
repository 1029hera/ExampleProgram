package BookManagement;

public class MyBookException extends RuntimeException implements C{

	private int errCode = ERR_GENERIC;
	
	public MyBookException() {
		super("MyBook 예외 발생");
	}
	
	public MyBookException(String msg) {
		super(msg);
	}
	
	public MyBookException(String msg, int errCode) {
		super(msg);
		this.errCode = errCode;
	}
	
	@Override
	public String getMessage() {
		String msg = "ERR-" + errCode + "]" + ERR_STR[errCode]
				+ " " + super.getMessage(); 
		return msg;
	}
	
}
