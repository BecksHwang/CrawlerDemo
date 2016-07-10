package ggzzjc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDChoiceField;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.util.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class testPdfBox {
	public static void main(String[] args) {
		testPdfBox test = new testPdfBox();
		try {
			// 取得url的内容
			test.geText("http://basic.10jqka.com.cn/api/pdf/ab1c8f6a613f9382.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void geText(String file) throws Exception {
		// 是否排序
		boolean sort = false;
		// pdf文件名
		String pdfFile = file;
		// 输入文本文件名称
		// String textFile = null;
		// 编码方式
		// String encoding = "UTF-8";
		// 开始提取页数
		int startPage = 1;
		// 结束提取页数
		int endPage = Integer.MAX_VALUE;
		// 文件输入流，生成文本文件
		// Writer output = null;
		// 内存中存储的PDF Document
		PDDocument document = null;
		try {
			try {
				// 首先当作一个URL来装载文件，如果得到异常再从本地文件系统//去装载文件
				URL url = new URL(pdfFile);
				document = PDDocument.load(url);
				// 获取PDF的文件名
				String fileName = url.getFile();
				System.out.println("fileName" + fileName);
				// 以原来PDF的名称来命名新产生的txt文件
				// if (fileName.length() > 4) {
				// File outputFile = new File(fileName.substring(0,
				// fileName.length() - 4) + ".txt");
				// textFile = outputFile.getName();
				// System.out.println("textFile" + textFile);
				// }
			} catch (MalformedURLException e) {

				// 如果作为URL装载得到异常则从文件系统装载
				document = PDDocument.load(pdfFile);
				// if (pdfFile.length() > 4) {
				// textFile = pdfFile.substring(0, pdfFile.length() - 4) +
				// ".txt";
				// }
			}
			// 文件输入流，写入文件倒textFile
			// output = new OutputStreamWriter(new FileOutputStream(textFile),
			// encoding);
			// PDFTextStripper来提取文本
			PDFTextStripper stripper = null;
			stripper = new PDFTextStripper();
			// 设置是否排序
			stripper.setSortByPosition(sort);
			// 设置起始页
			stripper.setStartPage(startPage);
			// 设置结束页
			stripper.setEndPage(endPage);
			// 调用PDFTextStripper的writeText提取并输出文本
			// stripper.writeText(document, output);
			String str = stripper.getText(document);
			String[] strA = str.split("\\t");
			for (String string : strA) {
				System.out.println(string);System.out.println("**********************************");
			}
			//System.out.println(str);
		} finally {
			// if (output != null) {
			// // 关闭输出流
			// output.close();
			// }
			if (document != null) {
				// 关闭PDF Document
				document.close();
			}
		}
	}

}
