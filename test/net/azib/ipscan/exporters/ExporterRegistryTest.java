/*
 * 
 */
package net.azib.ipscan.exporters;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

/**
 * ExporterRegistryTest
 *
 * @author Anton Keks
 */
public class ExporterRegistryTest {
	
	private ExporterRegistry exporterRegistry = new ExporterRegistry(new Exporter[] {new TXTExporter(), new CSVExporter()});
	
	@Test
	public void testIterator() {
		for (Iterator<?> i = exporterRegistry.iterator(); i.hasNext(); ) {
			Exporter exporter = (Exporter) i.next();
			assertNotNull(exporter);
			assertNotNull(exporter.getFilenameExtension());
		}
	}
	
	@Test
	public void testCreate() {
		Exporter exporter;
		
		exporter = exporterRegistry.createExporter("aa.abc." + new TXTExporter().getFilenameExtension());
		assertTrue(exporter instanceof TXTExporter);
		
		exporter = exporterRegistry.createExporter("/tmp/foo/megafile." + new TXTExporter().getFilenameExtension());
		assertTrue(exporter instanceof TXTExporter);
	}

	@Test
	public void testCreateFailed() {
		try {
			exporterRegistry.createExporter("noextension");
			fail();
		}
		catch (ExporterException e) {
			assertEquals("exporter.unknown", e.getMessage());
		}
		
		try {
			exporterRegistry.createExporter("unknown.extension");
			fail();
		}
		catch (ExporterException e) {
			assertEquals("exporter.unknown", e.getMessage());
		}
	}
	
}
