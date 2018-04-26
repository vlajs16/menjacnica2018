package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {

	public static MenjacnicaGUI mg;

	public static MenjacnicaInterface men = new Menjacnica();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mg = new MenjacnicaGUI();
					mg.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(mg.getContentPane(), "Da li ZAISTA zelite da izadjete iz apliacije",
				"Izlazak", JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void prikaziAboutProzor() {
		JOptionPane.showMessageDialog(mg.getContentPane(), "Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(mg.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				men.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mg.getContentPane(), e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(mg.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				men.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute();
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mg.getContentPane(), e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void prikaziSveValute() {
		MenjacnicaTableModel model = (MenjacnicaTableModel) (mg.getTable().getModel());
		model.staviSveValuteUModel(men.vratiKursnuListu());

	}

	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI(mg);
		prozor.setLocationRelativeTo(mg.getContentPane());
		prozor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI() {

		if (mg.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (mg.getTable().getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(model.vratiValutu(mg.getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(mg.getContentPane());
			prozor.setVisible(true);
		}
	}

	public static void prikaziIzvrsiZamenuGUI() {
		if (mg.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (mg.getTable().getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(model.vratiValutu(mg.getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(mg.getContentPane());
			prozor.setVisible(true);
		}
	}

	public static String izvrsiZamenu(Valuta valuta, boolean prodaja, double iznos) {
		double konacniIznos = men.izvrsiTransakciju(valuta, prodaja, iznos);
		return "" + konacniIznos;
	}

	public static void obrisiKurs(Valuta valuta) throws Exception {
		men.obrisiValutu(valuta);
		prikaziSveValute();
	}

	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, double prodajni, double kupovni,
			double srednji) throws Exception{

			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);

			// Dodavanje valute u kursnu listu
			men.dodajValutu(valuta);

	}

}
