package com.app.distrity.util.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ejb.Stateless;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.app.distrity.model.Detalle;
import com.app.distrity.model.Distribuidor;
import com.app.distrity.model.Empresa;
import com.app.distrity.model.OrdenVenta;
import com.app.distrity.model.Sucursal;

@Stateless
public class PDFDocumento implements IPDFDocumento {
	
	private OrdenVenta documento;
	private Distribuidor distribuidor;

	private PDPageContentStream contents;
	private PDDocument document;

	private int maxRowSize = 23;
	private int maxPageWithSummation = 16;
	private int breakPoint = 12;

	@Override
	public ByteArrayOutputStream getOrdenVentaPDFDocument(OrdenVenta documento, Distribuidor distribuidor) {
		this.documento = documento;
		this.distribuidor = distribuidor;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
        PDPage page = new PDPage();
        //PDDocument document = new PDDocument();
        document = new PDDocument();
		document.addPage(page);
        //PDPageContentStream contents;
		try {
			contents = new PDPageContentStream(document, page);
			
			this.printPDF();
			
			document.save(output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
            	document.close();    
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return output;
	}
    
	public void printPDF() throws IOException {
		this.printHeader();
		this.printSubContent(false);
		this.printSubContent(true);
		int rowY = 520;
		int numPrintedRows = 0;
		int rowsLeft = this.documento.getDetalles().size();
		printRowHeader(contents, rowY);
		printRowBackGround(contents, rowY-21, rowsLeft < this.maxPageWithSummation ? this.maxPageWithSummation : this.maxRowSize);
		BigDecimal totalCost = BigDecimal.ZERO;
		for (Detalle detalle : this.documento.getDetalles()) {	
			numPrintedRows++;
			rowY -= 20;
			this.printRow(rowY, detalle);
			totalCost = new BigDecimal(this.documento.getTotal());
			if(newPageRequired(numPrintedRows, rowsLeft)) {
				rowsLeft -= numPrintedRows;
				numPrintedRows = 0;
				maxRowSize = 30;
				maxPageWithSummation = 23;
				breakPoint = 18;
				rowY = 660;
				contents = newPage(rowY,rowsLeft < this.maxPageWithSummation ? this.maxPageWithSummation : this.maxRowSize);

			}
		}
		System.out.println("Total calculado " + totalCost + " Total persistido " + this.documento.getTotal());
		printSummery(totalCost);
		printFooter();
		contents.close();
	}
	
	
	
    private void printHeader() throws IOException {        
        /*PDImageXObject pdImage = PDImageXObject.createFromFile("/images/logo.png", document);
        final float width = 60f;
        final float scale = width / pdImage.getWidth();
        contents.drawImage(pdImage, 50, 720, width, pdImage.getHeight()*scale);
		*/
    	int xRight = 375;
    	int xLeft = 60;
    	int y = 720;
        PDFont headerFont = PDType1Font.HELVETICA_BOLD;
        PDFPrinter headerPrinter = new PDFPrinter(contents, headerFont, 16);
        headerPrinter.putText(xLeft, 740, this.distribuidor!=null ? this.distribuidor.getNombreComercial():"Nombre comercial");
        
        PDFont font = PDType1Font.HELVETICA;
        PDFPrinter textPrinter = new PDFPrinter(contents, font, 10);
        
        //textPrinter.putText(xLeft, y-12, "Razón social:");
        //textPrinter.putText(xLeft, y-24, "Dirección:");
        textPrinter.putText(xLeft, y-36, "Teléfono:");
        textPrinter.putText(xLeft, y-48, "Email:");
        textPrinter.putText(xLeft, y-60, "Web:");
        
        textPrinter.putText(xLeft, y-12, this.distribuidor.getRazonSocial()!=null ? this.distribuidor.getRazonSocial():"Razón social");
        textPrinter.putText(xLeft, y-24, this.distribuidor.getSucursales().get(0).getDireccion()!=null ? this.distribuidor.getSucursales().get(0).getDireccion().getUbicacion().substring(0, 60) :"Dirección");
        textPrinter.putText(xLeft+50, y-36, this.distribuidor!=null ? this.distribuidor.getTelefono():"Teléfono");
        textPrinter.putText(xLeft+50, y-48, this.distribuidor!=null ? this.distribuidor.getEmail():"Email");
        textPrinter.putText(xLeft+50, y-60, this.distribuidor!=null ? this.distribuidor.getWeb():"Web");

        Color color = new Color(200, 200, 200);
        PDFPrinter invoiceHeaderPrinter = new PDFPrinter(contents, font, 24, color);
        invoiceHeaderPrinter.putText(xRight+50, 740, "PEDIDO");     

        textPrinter.putText(xRight, y-12, "Nro. Documento:");
        textPrinter.putText(xRight, y-24, "Emisión:");
        textPrinter.putText(xRight, y-36, "Vencimiento:");
        textPrinter.putText(xRight, y-48, "Moneda:");
        textPrinter.putText(xRight, y-60, "Usuario:");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        textPrinter.putText(xRight+100, y-12, this.documento.getNumeroDocumento()!=null ? this.documento.getNumeroDocumento():"");
        textPrinter.putText(xRight+100, y-24, this.documento.getFecha()!=null ? dateFormat.format(this.documento.getFecha()):"");
        textPrinter.putText(xRight+100, y-36, this.documento.getVencimiento()!=null ? dateFormat.format(this.documento.getVencimiento()):"");
        textPrinter.putText(xRight+100, y-48, this.documento.getMoneda().getDescripcion()!=null ? this.documento.getMoneda().getDescripcion():"");
        textPrinter.putText(xRight+100, y-60, this.documento.getUsuario().getUsername()!=null ? this.documento.getUsuario().getUsername():"");
    }	
	
	public void printSubContent(boolean rightSide) throws IOException {
		PDFont font = PDType1Font.HELVETICA;
        Color color = new Color(80, 80, 80);
        int x = rightSide ? 375 : 60;
        int y = 720-60;
        PDFPrinter headerPrinter = new PDFPrinter(contents, font, 10);
        PDFPrinter contentPrinter = new PDFPrinter(contents, font, 10, color);
        y -= 24;
        Empresa cliente = this.documento.getCliente();
        if (cliente!=null) {  
	        if (!rightSide) {
	        	headerPrinter.putText(x, y, "Envío:");
	        	contentPrinter.putText(x, y-12, !cliente.getSucursales().isEmpty() ? ((Sucursal)this.documento.getCliente().getSucursales().get(0)).getDireccion().getUbicacion().substring(0, 60):"");
		        contentPrinter.putText(x, y-24, "Estado:");
		        contentPrinter.putText(x+50, y-24, this.documento.getEstado()!=null ? this.documento.getEstado().toString():"");
	        	contentPrinter.putText(x, y-36, "Horario:");
		        contentPrinter.putText(x+50, y-36, cliente.getHorario()!=null ? cliente.getHorario():"");
	        	contentPrinter.putText(x, y-48, "Teléfono:");
		        contentPrinter.putText(x+50, y-48, cliente.getTelefono()!=null ? cliente.getTelefono():"");
	        } else {
	        	headerPrinter.putText(x, y, "Cliente:");
		        contentPrinter.putText(x, y-12, cliente.getNombreComercial()!=null ? cliente.getNombreComercial():"");
		        contentPrinter.putText(x, y-24, cliente.getRazonSocial()!=null ? cliente.getRazonSocial():"");
		        contentPrinter.putText(x, y-36, "Web:");
		        contentPrinter.putText(x+50, y-36, cliente.getWeb()!=null ? this.documento.getCliente().getWeb():"");
		        contentPrinter.putText(x, y-48, "Email:");
		        contentPrinter.putText(x+50, y-48, cliente.getEmail()!=null ? cliente.getEmail():"");
	        }
        }
    }
	
    /*
    public void printShipData() throws IOException {        
        
        Color fillColor = new Color(230, 230, 230);
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.setNonStrokingColor(fillColor);
        contents.addRect(50, 570, 520, 20);
        contents.fillAndStroke();
        contents.addRect(50, 550, 520, 20);
        contents.stroke();

        final int headerY = 577;
        PDFont font = PDType1Font.HELVETICA;
        PDFPrinter headerPrinter = new PDFPrinter(contents, font, 12);
        headerPrinter.putText(60, headerY, "Ship. number");
        headerPrinter.putText(160, headerY, "Sales Rep.");
        headerPrinter.putText(280, headerY, "Ship date");
        headerPrinter.putText(340, headerY, "Ship via");
        headerPrinter.putText(450, headerY, "Terms");
        headerPrinter.putText(510, headerY, "Due date");

        final int textY = 557;
        PDFPrinter textPrinter = new PDFPrinter(contents, font, 8);
        textPrinter.putText(60, textY, "this.getShipNumber()");
        textPrinter.putText(160, textY, "this.getSalesRep()");
        textPrinter.putText(280, textY, "this.getShipDateString()");
        textPrinter.putText(340, textY, "this.getShipVia()");
        textPrinter.putText(450, textY, "this.getTerms()");
        textPrinter.putText(510, textY, "this.getDueDateString()");
    }*/

	private PDPageContentStream newPage(int rowY, int numRows) throws IOException {
		contents.close();
		PDPage pdfPage = new PDPage();
		document.addPage(pdfPage);		
		contents = new PDPageContentStream(document, pdfPage);
		this.printHeader();
		printRowHeader(contents, rowY);
		printRowBackGround(contents, rowY-21, numRows);		
		return contents;
	}

	private boolean newPageRequired(int numPrintedRows, int rowsLeft) {
		if(numPrintedRows >= this.maxRowSize) return true;
		if(this.maxPageWithSummation < rowsLeft && rowsLeft < this.maxRowSize) {
			if(numPrintedRows >= this.breakPoint) return true;
		}
		return false;
	}

	public void printSummery(BigDecimal totalCost) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        Color fillColor = new Color(240, 240, 240);
        contents.setNonStrokingColor(fillColor);        

        PDFPrinter summeryLabelPrinter = new PDFPrinter(contents, PDType1Font.HELVETICA_BOLD, 8);
        PDFPrinter summeryValuePrinter = new PDFPrinter(contents, PDType1Font.HELVETICA, 12);

    	BigDecimal subTotal = totalCost.multiply(new BigDecimal(0.8f));
    	BigDecimal vatValue = totalCost.multiply(new BigDecimal(0.2f));
    	subTotal = subTotal.setScale(2, RoundingMode.HALF_EVEN);    	
    	vatValue = vatValue.setScale(2, RoundingMode.HALF_EVEN);
    	totalCost = totalCost.setScale(2, RoundingMode.HALF_EVEN);

    	int summeryStartY = 171;
    	int x = 450;

		summeryLabelPrinter.putText(x+1, summeryStartY, "Subtotal");
        contents.addRect(x, summeryStartY-20, 120, 16);
        contents.stroke();
        String moneda = this.documento.getMoneda().getSigla();
        summeryValuePrinter.putTextToTheRight(x+116, summeryStartY-16, moneda + " " + this.documento.getTotal()); /* subTotal.toString() */

		summeryLabelPrinter.putText(x+1, summeryStartY - 30, "IVA");
        contents.addRect(x, summeryStartY-30-20, 120, 16);
        contents.stroke();
        summeryValuePrinter.putTextToTheRight(x+116, summeryStartY - 30 - 16, moneda + " " + this.documento.getTotal()*0.22); /*vatValue.toString()*/

		summeryLabelPrinter.putText(x+1, summeryStartY - 60, "Total");
        contents.addRect(x, summeryStartY-60-20, 120, 16);
        contents.stroke();
        summeryValuePrinter.putTextToTheRight(x+116, summeryStartY - 60 - 16, moneda + " " + this.documento.getTotal()*1.22);        
	}


	public void printRowBackGround(PDPageContentStream contents, int rowY, int numRows) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        Color fillColor = new Color(240, 240, 240);
        contents.setNonStrokingColor(fillColor);
		boolean odd = true;
        for(int i=0; i<numRows; i++) {
	        if(odd) {
		        contents.addRect(51, rowY, 518, 20);
		        contents.fill();
	        }
        	contents.moveTo(50, rowY);
        	contents.lineTo(50, rowY+20);
        	contents.moveTo(570, rowY);
        	contents.lineTo(570, rowY+20);
        	contents.stroke();
			rowY -= 20;
			odd = !odd;
        }
    	contents.moveTo(50, rowY+20);
    	contents.lineTo(570, rowY+20);
    	contents.stroke();
	}
	
	public void printRowHeader(PDPageContentStream contents, int headerY) throws IOException {
        Color fillColor = new Color(230, 230, 230);
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.setNonStrokingColor(fillColor);
        contents.addRect(50, headerY, 520, 20);
        contents.fillAndStroke();
        PDFont font = PDType1Font.HELVETICA;
        PDFPrinter headerPrinter = new PDFPrinter(contents, font, 12);
        headerPrinter.putText(60, headerY+7, "Código");
        headerPrinter.putText(120, headerY+7, "Artículo");
        headerPrinter.putText(340, headerY+7, "UN");
        headerPrinter.putText(380, headerY+7, "Cant.");
        headerPrinter.putText(420, headerY+7, "Dto.");
        headerPrinter.putText(460, headerY+7, "Precio");
        headerPrinter.putText(520, headerY+7, "Parcial");
	}
	
    public void printRow(int rowY, Detalle detalle) throws IOException {        
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        PDFont font = PDType1Font.HELVETICA;
        PDFPrinter textPrinter = new PDFPrinter(contents, font, 8);
        textPrinter.putText(60, rowY+7, detalle.getArticulo().getCodigo());
        textPrinter.putText(120, rowY+7, detalle.getArticulo().getNombre()+". "+detalle.getArticulo().getDescripcion());
        textPrinter.putText(340, rowY+7, detalle.getArticulo().getUnidad().getCodigo());       
        textPrinter.putText(380, rowY+7, detalle.getCantidad().toString());
        textPrinter.putText(420, rowY+7, detalle.getDescuento().toString());
        textPrinter.putText(460, rowY+7, detalle.getPrecio().toString());
        Double parcial = detalle.getPrecio()*detalle.getCantidad();
        textPrinter.putText(520, rowY+7, parcial.toString());
    }
	

	public void printFooter() throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.addRect(50, 35, 370, 135);
        contents.stroke();

        String observaciones = this.documento.getEstado().toString();
        PDFPrinter footerLabelPrinter = new PDFPrinter(contents, PDType1Font.HELVETICA_BOLD, 8);
        PDFPrinter footerValuePrinter = new PDFPrinter(contents, PDType1Font.HELVETICA, 8);
        footerLabelPrinter.putText(50, 172, "Notes");
        int rowY = 160;
        StringBuilder sb = new StringBuilder();
        for(String s : observaciones.split(" ")) {
        	if(footerValuePrinter.widthOfText(sb.toString() + " " + s) > 365) {
	        	if(rowY < 50) {
	        		sb.append("...");
		        	footerValuePrinter.putText(55, rowY, sb.toString());
		        	sb = new StringBuilder();
		        	break;
	        	}
	        	footerValuePrinter.putText(55, rowY, sb.toString());        	
	        	rowY -= 10;
	        	sb = new StringBuilder();
        	}        	
        	sb.append(s);
        	sb.append(" ");
        }
    	footerValuePrinter.putText(55, rowY, sb.toString());        	
	}

	/*
	public List<InvoiceRow> getRows() {
		return this.rows;
	}
    public void addRow(InvoiceRow row) {
        this.rows.add(row);
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getNotes() {
        return this.notes;
    }*/
}