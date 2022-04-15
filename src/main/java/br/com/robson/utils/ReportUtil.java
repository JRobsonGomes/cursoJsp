package br.com.robson.utils;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static String separator = File.separator;
	private static final String REPORTS_PATH = separator + "WEB-INF" + separator + "classes" + separator + "relatorios" + separator;

	public static byte[] gerarRelatorioPDF(List<?> dados, String nome, Map<String, Object> params, ServletContext context) throws JRException {
		try {
			var dataSource = new JRBeanCollectionDataSource(dados);
			URL pathReport = context.getResource(getReportsPath() + nome + ".jasper"); // Colocando dentro da pasta main/resouces
			var printJasper = JasperFillManager.fillReport(pathReport.getFile(), params, dataSource);
			
			return JasperExportManager.exportReportToPdf(printJasper);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public byte[] gerarRelatorioPDF(List<?> dados, String nome, ServletContext context) throws JRException {
		try {
			String separator = File.separator;
			var dataSource = new JRBeanCollectionDataSource(dados);
//			var pathReport = context.getRealPath("relatorios") + File.separator + nome + ".jasper"; //Colocando dentro da pasta webapp/relatorios
			URL pathReport = context.getResource(separator + "WEB-INF" + separator + "classes" + separator + "relatorios" + separator + nome + ".jasper"); // Colocando dentro da pasta main/resouces
			var printJasper = JasperFillManager.fillReport(pathReport.getFile(), new HashMap<>(), dataSource);
			
			return JasperExportManager.exportReportToPdf(printJasper);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static String getReportsPath() {
		return REPORTS_PATH;
	}
}
