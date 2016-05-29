package stef.testareProiectCTS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import stef.patterns.facade.AppFacade;
import stef.patterns.factory.Mesaj;
import stef.patterns.factory.MesajAbstract;
import stef.patterns.factory.MesajImportant;
import stef.patterns.factory.MesajUrgent;
import stef.patterns.observer.Client;
import stef.patterns.observer.ClientGermania;
import stef.patterns.observer.ClientRomania;
import stef.utils.Prioritate;

public class TestFacade {
	private static FileReader reader;
	private static BufferedReader bufferedReader;
	private static AppFacade app;
	private List<MesajAbstract> mesaje = new ArrayList<MesajAbstract>();
	private FileReader r;
	private BufferedReader br;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Before class - citesc din fisier clienti test");
		app = new AppFacade();
		reader = new FileReader("testClienti.txt");
		bufferedReader = new BufferedReader(reader);
		String linie = null;
		while((linie = bufferedReader.readLine()) != null && linie.length() != 0){
			System.out.println(linie);
			String[] cuv = linie.split(" ");
			if(cuv[1].equalsIgnoreCase("roman")){
				Client roman = new ClientRomania(cuv[2], cuv[3], cuv[4], Float.parseFloat(cuv[5]));
				roman.seteazaCont();
				app.adaugaObserver(roman);
			}
			if(cuv[1].equalsIgnoreCase("german")){
				app.adaugaObserver(new ClientGermania(cuv[2], cuv[3], cuv[4], Float.parseFloat(cuv[5])));
			}
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("After class - inchid fisierul cu clientii test");
		bufferedReader.close();
		reader.close();
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("SetUp - citesc mesajele test din fisier");
		this.r = new FileReader("testMesaje.txt");
		this.br = new BufferedReader(r);
		String linie = null;
		while((linie = br.readLine()) != null && linie.length() != 0){
			System.out.println(linie);
			String[] cuv = linie.split(" ");
			if(cuv[1].equalsIgnoreCase("urgent")){
				this.mesaje.add(new MesajUrgent(cuv[2]));
			}
			if(cuv[1].equalsIgnoreCase("important")){
				this.mesaje.add(new MesajImportant(cuv[2]));
			}
			if(cuv[1].equalsIgnoreCase("informativ")){
				this.mesaje.add(new Mesaj(cuv[2]));
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("TearDown - inchid fisierul cu mesajele test");
		this.br.close();
		this.r.close();
	}

	@Test
	public void testAprobaSiPublicaMesaj() {
		for(MesajAbstract m : this.mesaje){
			app.aprobaSiPublicaMesaj(m);
		}
	}
	
	@Test
	public void testAprobaSiPublicaMesajNull() {
		try{
			app.aprobaSiPublicaMesaj(null);
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}
	
	@Test
	public void testAprobaSiPublicaMesajCuContinutNull() {
		try{
			app.aprobaSiPublicaMesaj(app.genereazaMesaj(Prioritate.IMPORTANT, null));
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}

}
