package com.app.distrity.util.pdf;

import java.io.ByteArrayOutputStream;

import com.app.distrity.model.Distribuidor;
import com.app.distrity.model.OrdenVenta;

public interface IPDFDocumento {

	public ByteArrayOutputStream getOrdenVentaPDFDocument(OrdenVenta documento, Distribuidor distribuidor);

}