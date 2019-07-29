package mail.bean;

import admin.bean.AdminDTO;

public interface Mailing {
	public String checkNum();
	public String getKey(int length);
	public MessageDTO sendConfirmMail(MessageDTO messageDTO,String checkNum);
	public MessageDTO sendWelcomeMail(MessageDTO messageDTO);
	public MessageDTO sendgeneralMail(MessageDTO messageDTO);
	public MessageDTO sendOrderMail(MessageDTO messageDTO);
	public MessageDTO sendGoodbyeMail(MessageDTO messageDTO);
	public void sendMail(AdminDTO adminDTO,MessageDTO messageDTO);
	public MessageDTO sendResetPwdMail(MessageDTO messageDTO, String resetPwd);
}
