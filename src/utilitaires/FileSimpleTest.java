package utilitaires;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileSimpleTest {

	@Test
	public void testFileSimple() {
		FileSimple fileTest = new FileSimple();
		assertTrue(fileTest != null);
	}
	
	
	@Test
	public void testAjouterElement() {
		FileSimple fileTest = new FileSimple();
		assertTrue(fileTest.estVide());
		fileTest.ajouterElement(123);
		assertTrue(!fileTest.estVide());
	}
	
	@Test
	public void testEnleverElement() {
		FileSimple fileTest = new FileSimple();
		
		assertTrue(fileTest.estVide());
		
		fileTest.ajouterElement(123);
		assertTrue(!fileTest.estVide());
		
		Integer element = (Integer)fileTest.enleverElement();
		assertEquals((Integer)123, element);
		assertTrue(fileTest.estVide());
	}
	
}
