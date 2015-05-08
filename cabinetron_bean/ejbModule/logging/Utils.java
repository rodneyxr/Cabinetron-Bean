package logging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Utils {

	public static String blobify(Serializable o) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		byte[] bytes;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(o);
			bytes = bos.toByteArray();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} finally {
			try {
				if (out != null)
					out.close();
				bos.close();
			} catch (IOException ioe) {
				return null;
			}
		}
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static Object deblobify(String blob) {
		byte[] bytes = Base64.getDecoder().decode(blob);
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		Object o;
		try {
			in = new ObjectInputStream(bis);
			o = in.readObject();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		} finally {
			try {
				bis.close();
				if (in != null)
					in.close();
			} catch (IOException ioe) {
				return null;
			}
		}
		return o;
	}

}
