package com.km.parcelorganizer.features.email;

import com.km.parcelorganizer.features.user.User;
import com.km.parcelorganizer.features.user.password.PasswordToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

	private final JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendForgotPasswordEmail(User user, PasswordToken passwordToken) throws MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo("kevin.martin@live.nl");
		helper.setFrom("parcelorganizer@gmail.com");
		helper.setSubject("Recover Parcel Organizer Password");
		helper.setText("<html>\n" +
				"\n" +
				"<head>\n" +
				"\n" +
				"    <title></title>\n" +
				"    <style>\n" +
				"        .container {\n" +
				"            padding: 16px;\n" +
				"        }\n" +
				"\n" +
				"        td {\n" +
				"            padding: 8px;\n" +
				"        }\n" +
				"\n" +
				"        body * {\n" +
				"            font-family: Arial, Helvetica, sans-serif;\n" +
				"            font-weight: 500;\n" +
				"            font-size: 15px;\n" +
				"        }\n" +
				"\n" +
				"        .logo {\n" +
				"            width: 60px;\n" +
				"            height: 60px;\n" +
				"        }\n" +
				"\n" +
				"        .logo-text {\n" +
				"            font-size: 20px;\n" +
				"            font-weight: 600;\n" +
				"        }\n" +
				"    </style>\n" +
				"</head>\n" +
				"\n" +
				"<body>\n" +
				"    <table>\n" +
				"        <tr>\n" +
				"            <td>Hello " + user.getName() + " ,</td>\n" +
				"        </tr>\n" +
				"        <tr>\n" +
				"            <td>We have received your request for a password reset. Click the link below, if that doesn't work paste the\n" +
				"                link in your browser.</td>\n" +
				"        </tr>\n" +
				"        <tr>\n" +
				"            <td>http://parcelorganizer.nl/reset-password?token=" + passwordToken.getToken() + "</td>\n" +
				"        </tr>\n" +
				"        <tr>\n" +
				"            <td>If you did not initiate this password reset, please contact us.</td>\n" +
				"        </tr>\n" +
				"\n" +
				"        <table>\n" +
				"            <tr>\n" +
				"                <td><img class=logo\n" +
				"                        src=\"http://cdn.mcauto-images-production.sendgrid.net/562bf6d29d928091/226c49f8-9afd-469f-a817-c066ca66a961/504x504.png\"\n" +
				"                        alt=\"\"></td>\n" +
				"                <td class=logo-text>\n" +
				"                    Parcel Organizer\n" +
				"                </td>\n" +
				"            </tr>\n" +
				"        </table>\n" +
				"\n" +
				"    </table>\n" +
				"</body>\n" +
				"\n" +
				"</html>", true);

		javaMailSender.send(message);
	}

}
