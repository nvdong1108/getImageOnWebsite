import java.io.BufferedReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.jsoup.Jsoup;

public class CutStringHTML {

	public static void main(String[] args) {
		String name_picture;
		String link_dowload;
		try {
			BufferedReader in = new BufferedReader(new FileReader("D:/vandong/flag.html"));
			String str;
			while ((str = in.readLine()) != null) {
				if (str.contains("http://flagpedia.net/")) {
					int start = str.indexOf("http://flagpedia.net/");
					int begin = str.indexOf("</a></td>");
					if (start != -1 && begin != -1 && start < begin && (begin - start) < 100) {
						String link = str.substring(start, begin);
						int end_link = link.indexOf(">") - 1;
						String link_dowload_html = link.substring(0, end_link);
						int indexk = link_dowload_html.indexOf("net/");
						name_picture = "";
						name_picture = link_dowload_html.substring((indexk + 4));
						name_picture = name_picture + ".png";
						name_picture = name_picture.replace("-", "_");
						
						// System.out.println(link_dowload_html + " # " +
						// name_picture);
						String html = Jsoup.connect(link_dowload_html).get().html();
						{
							File fn = new File("file_html.txt");
							FileWriter fw2 = new FileWriter(fn);
							fw2.write(html);
							fw2.flush();
							fw2.close();
							FileReader readFile = new FileReader(fn);
							BufferedReader bf = new BufferedReader(readFile);
							String stmp;
							while ((stmp = bf.readLine()) != null) {
								if (stmp.contains("//flags.fmcdn.net/data/flags/")) {
									int start2 = stmp.indexOf("//flags.fmcdn.net/data/flags/");
									int begin2 = stmp.indexOf(".png");
									if (start2 != -1 && begin2 != -1 && start2 < begin2) {
										String link_picture_dowlaod = "";
										link_picture_dowlaod = stmp.substring(start2, (begin2 + 4));
										if (!link_picture_dowlaod.contains("h40")) {
											link_dowload = "";
											link_dowload = "http:" + link_picture_dowlaod;
											link_dowload = link_dowload.trim();
											System.out.println(name_picture);
											URL url = new URL(link_dowload);
											try {
												InputStream inl = url.openStream();
												Files.copy(inl, Paths.get(name_picture),
														StandardCopyOption.REPLACE_EXISTING);
												inl.close();
											} catch (Exception e) {
												System.out.println(e);
											}
										}
									}
								}
							}
							// reading file

						}
					}
				}

			}
			in.close();
		} catch (IOException e) {
		}

	}
}
