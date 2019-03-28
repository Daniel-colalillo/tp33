package utilitaires;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vecteur2DTest {

	@Test
	public void testVecteur2D() {
		Vecteur2D vec = new Vecteur2D();
		assertEquals(0, vec.getX(), Vecteur2D.PRECISION);
		assertEquals(0, vec.getY(), Vecteur2D.PRECISION);
	}

	@Test
	public void testVecteur2D_Parametres() {
		Vecteur2D vec = new Vecteur2D(3.14, 5.42);
		assertEquals(3.14, vec.getX(), Vecteur2D.PRECISION);
		assertEquals(5.42, vec.getY(), Vecteur2D.PRECISION);
	}

	@Test
	public void testVecteur2D_Copie() {
		Vecteur2D vref = new Vecteur2D(3.14, 5.42);
		Vecteur2D vec = new Vecteur2D(vref);
		
		assertEquals(3.14, vec.getX(), Vecteur2D.PRECISION);
		assertEquals(5.42, vec.getY(), Vecteur2D.PRECISION);
	}

	@Test
	public void testGetLongueur() {
		Vecteur2D vec = new Vecteur2D(3.0, 4.0);
		assertEquals(5.0, vec.getLongueur(), Vecteur2D.PRECISION);
	}

	@Test
	public void testGetAngle() {
		Vecteur2D vec = new Vecteur2D(1.0, 1.0);
		assertEquals(Math.PI / 4, vec.getAngle(), Vecteur2D.PRECISION);
	}

	@Test
	public void testDifference() {
		Vecteur2D v1 = new Vecteur2D(3.14, 5.42);
		Vecteur2D v2 = new Vecteur2D(1.0, 1.0);
		Vecteur2D vec = v1.difference(v2);
		
		assertEquals(-2.14, vec.getX(), Vecteur2D.PRECISION);
		assertEquals(-4.42, vec.getY(), Vecteur2D.PRECISION);
	}

	@Test
	public void testSomme() {
		Vecteur2D v1 = new Vecteur2D(3.14, 5.42);
		Vecteur2D v2 = new Vecteur2D(1.0, 1.0);
		Vecteur2D vec = v1.somme(v2);
		
		assertEquals(4.14, vec.getX(), Vecteur2D.PRECISION);
		assertEquals(6.42, vec.getY(), Vecteur2D.PRECISION);
	}

	@Test
	public void testDiviser() {
		Vecteur2D vec = new Vecteur2D(4.14, 6.42);
		vec.diviser(2.0);
		
		assertEquals(2.07, vec.getX(), Vecteur2D.PRECISION);
		assertEquals(3.21, vec.getY(), Vecteur2D.PRECISION);
	}

	@Test
	public void testToString() {
		Vecteur2D vec = new Vecteur2D(3.14, 5.42);
		
		assertEquals("[3.14,5.42]", vec.toString());
	}

	@Test
	public void testEqualsVecteur2D() {
		Vecteur2D vref = new Vecteur2D(3.14, 5.42);
		Vecteur2D vec = new Vecteur2D(vref);
		
		assertTrue(vref.equals(vec));

	}

	@Test
	public void testCompareTo() {
		Vecteur2D vref = new Vecteur2D(3.14, 5.42);
		Vecteur2D vec = new Vecteur2D(vref);
		
		assertEquals(0, vec.compareTo(vref));
		assertEquals(1, vec.compareTo(new Vecteur2D(1.0, 1.0)));
		assertEquals(-1, vec.compareTo(new Vecteur2D(10.0, 10.0)));
	}

}
