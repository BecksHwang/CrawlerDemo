package com.becks.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml",
		"classpath:spring-redis.xml", "classpath:spring-env.xml" })
public class OfficeExcelUtil {

	//
	@Test
	public void test() {
		try {
			InputStream is = new FileInputStream("C:\\Users\\admin\\Desktop\\基金管理\\基金和十大重仓股.xls");
			// 声名一个工作薄
			Workbook rwb = Workbook.getWorkbook(is);
			// 获得工作薄的个数
			rwb.getNumberOfSheets();
			// 在Excel文档中，第一张工作表的缺省索引是0
			Sheet st = rwb.getSheet(0);
			// 通用的获取cell值的方式,getCell(int column, int row) 行和列
			int rows = st.getRows();
			int cols = st.getColumns();
			System.out.println("当前工作表的名字:" + st.getName());
			System.out.println("总行数:" + rows);
			System.out.println("总列数:" + cols);
			/*for (int i = 1; i < rows; i++) {
				CmsFund cf = new CmsFund();
				String fundCode = null;
				String fundName = null;
				String holdingsCode = null;
				Float percent = null;
				for (int j = 0; j < cols; j++) {
					CmsHoldings ch = new CmsHoldings();
					Cell c1 = st.getCell(j, i);
					String content = c1.getContents().trim();
					if (StringUtils.isNotBlank(content)) {
						System.out.print("---" + j + "---" + content);
						if (j == 0) {
							fundCode = content;
							cf.setFundCode(content);
						} else if (j == 1) {
							cf.setFundName(content);
							fundName = content;
						} else if (j % 2 == 0) {
							holdingsCode = content;
						} else {
							percent = Float.valueOf(content);
						}
						if (j % 2 != 0 && j != 1 && j != 0) {
							ch.setHoldingsCode(holdingsCode);
							ch.setPercent(percent);
							ch.setFundCode(fundCode);
							ch.setFundName(fundName);
							cmsHoldingsService.insert(ch);
						}

					} else {
						continue;
					}
				}
				cmsFundService.insert(cf);
				System.out.println("");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出错了");
		} finally {
		}
	}
}
