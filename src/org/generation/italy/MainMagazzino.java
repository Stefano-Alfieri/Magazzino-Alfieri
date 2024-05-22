package org.generation.italy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.generation.italy.model.Movimento;

public class MainMagazzino {

	public static void main(String[] args) {
		// dichiarazioni dati
		HashMap<String, String> clienti = new HashMap<String, String>();
		HashMap<String, String> fornitori = new HashMap<String, String>();
		HashMap<String, String> prodotti = new HashMap<String, String>();
		HashMap<String, String> tipologieMovimentoE = new HashMap<String, String>();
		HashMap<String, String> tipologieMovimentoU = new HashMap<String, String>();
		HashMap<String, Integer> giacenzaProd = new HashMap<String, Integer>();
		ArrayList<Movimento> listaMovimenti = new ArrayList<Movimento>();
		HashMap<String, List<String>> movimentiCliente = new HashMap<String, List<String>>();
		HashMap<String, List<String>> movimentiFornitore = new HashMap<String, List<String>>();
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Movimento m = new Movimento();
		String risposta, rispostaRestart, risp;
		boolean sceltaSbagliata = false, entrSba = false, uscSba = false, giacSba = false;
		boolean uscita = false, entrata = false, giacenza = false, ricerca = false;
		int k = 1, l = 1;
		// inserimento clienti
		clienti.put("01", "Stefano Alfieri");
		clienti.put("02", "Peter Hanna");
		clienti.put("03", "Samuele Lanza");
		clienti.put("04", "Federica Zaccaro");
		// inserimento fornitori
		fornitori.put("01", "Enzo Colluto");
		fornitori.put("02", "Luca Grillo");
		fornitori.put("03", "Mario Rossi");
		fornitori.put("04", "Franco Verdi");
		// inserimento prodotti
		prodotti.put("01", "Pane");
		prodotti.put("02", "Acqua");
		prodotti.put("03", "Pasta");
		prodotti.put("04", "Fagioli");
		// inserimento giacenza prodotti
		giacenzaProd.put("01", 50);
		giacenzaProd.put("02", 30);
		giacenzaProd.put("03", 100);
		giacenzaProd.put("04", 20);
		// inserimento movimenti
		tipologieMovimentoE.put("E01", "acquisto da fornitore");
		tipologieMovimentoE.put("E02", "reso da cliente");
		tipologieMovimentoE.put("E03", "produzione interna");
		tipologieMovimentoE.put("E04", "spostamento da altro magazzino");
		tipologieMovimentoU.put("U01", "vendita a cliente");
		tipologieMovimentoU.put("U02", "reso a fornitore");
		tipologieMovimentoU.put("U03", "sostituzione in garanzia");
		tipologieMovimentoU.put("U04", "spostamento a altro magazzino");
		// inizio
		System.out.println("Benvenuto nel magazzino");
		// ciclo per restart
		do {
			// ciclo per controllo ripsosta valida
			do {
				sceltaSbagliata = false;
				System.out.println(
						"cosa vuoi fare? movimento in entrata (1) o movimento in uscita (2) o giacenzaza prodotto (3) o ricerca movimento (4)?");
				risposta = sc.nextLine();
				if (risposta.equals("1")) {
					System.out.println("hai selezionato movimento in entrata");
					entrata = true;
				} else if (risposta.equals("2")) {
					System.out.println("hai selezionato movimento in uscita");
					uscita = true;
				} else if (risposta.equals("3")) {
					System.out.println("hai selezionato giacenza prodotto");
					giacenza = true;
				} else if (risposta.equals("4")) {
					System.out.println("hai selezionato ricerca movimento");
					ricerca = true;
				} else {
					System.out.println("hai inserito una scelta sbagliata, reinserire");
					sceltaSbagliata = true;
				}
			} while (sceltaSbagliata == true);
			// sessione movimento in entrata
			if (entrata == true) {
				do {
					entrSba = false;
					for (int i = 0; i <= listaMovimenti.size(); i++) {
						System.out.print("Inserisci la data del movimento in entrata: ");
						m.dataMov = LocalDate.parse(sc.nextLine(), df);
						System.out.print("Inserisci il codice prodotto: ");
						m.codiceprod = sc.nextLine();
						System.out.print("Inserisci la quantità del prodotto: ");
						m.quantita = sc.nextInt();
						sc.nextLine();
						giacenzaProd.put(m.codiceprod, (giacenzaProd.get(m.codiceprod) + m.quantita));
						System.out.println("Inserisci il codice del movimento in entrata tra i seguenti: \n"
								+ tipologieMovimentoE);
						m.codiceMov = sc.nextLine();
						if (m.codiceMov.equals("E01")) {
							System.out.println("inserire il codice fornitore");
							m.codiceForn = sc.nextLine();
							if (!movimentiFornitore.containsKey(m.codiceForn)) {
								movimentiFornitore.put(m.codiceForn, new ArrayList<String>());
							}
							movimentiFornitore.get(m.codiceForn).add("E" + l);
							l++;
						} else if (m.codiceMov.equals("E02")) {
							System.out.println("inserire il codice cliente");
							m.codiceCliente = sc.nextLine();
							if (!movimentiCliente.containsKey(m.codiceCliente)) {
								movimentiCliente.put(m.codiceCliente, new ArrayList<String>());
							}
							movimentiCliente.get(m.codiceCliente).add("E" + l);
							l++;
						}
						if (tipologieMovimentoE.containsKey(m.codiceMov)) {
							listaMovimenti.add(m);
							System.out.println("il movimento effettuato è: " + tipologieMovimentoE.get(m.codiceMov));
							System.out.println("effettuato in data " + listaMovimenti.get(i).dataMov.format(df));
							System.out.println("per l'articolo " + prodotti.get(m.codiceprod));
							entrata = false;
							break;
						} else {
							System.out.println("movimento inserito non corretto, reinserire i dati");
							entrSba = true;
						}
					}
				} while (entrSba == true);
				// sessione movimento in uscita
			} else if (uscita == true) {
				do {
					uscSba = false;
					for (int i = 0; i <= listaMovimenti.size(); i++) {
						System.out.print("Inserisci la data del movimento in uscita: ");
						m.dataMov = LocalDate.parse(sc.nextLine(), df);
						System.out.print("Inserisci il codice prodotto: ");
						m.codiceprod = sc.nextLine();
						System.out.print("Inserisci la quantità del prodotto: ");
						m.quantita = sc.nextInt();
						sc.nextLine();
						giacenzaProd.put(m.codiceprod, (giacenzaProd.get(m.codiceprod) - m.quantita));
						System.out.println(
								"Inserisci il codice del movimento in uscita tra i seguenti: \n" + tipologieMovimentoU);
						m.codiceMov = sc.nextLine();
						if (m.codiceMov.equals("U02")) {
							System.out.println("inserire il codice fornitore");
							m.codiceForn = sc.nextLine();
							if (!movimentiFornitore.containsKey(m.codiceForn)) {
								movimentiFornitore.put(m.codiceForn, new ArrayList<String>());
							}
							movimentiFornitore.get(m.codiceForn).add("U" + k);
							k++;
						} else if (m.codiceMov.equals("U01")) {
							System.out.println("inserire il codice cliente");
							m.codiceCliente = sc.nextLine();
							if (!movimentiCliente.containsKey(m.codiceCliente)) {
								movimentiCliente.put(m.codiceCliente, new ArrayList<String>());
							}
							movimentiCliente.get(m.codiceCliente).add("U" + k);
							k++;
						}
						if (tipologieMovimentoU.containsKey(m.codiceMov)) {
							listaMovimenti.add(m);
							System.out.println("il movimento effettuato è: " + tipologieMovimentoU.get(m.codiceMov));
							System.out.println("effettuato in data " + listaMovimenti.get(i).dataMov.format(df));
							System.out.println("per l'articolo " + prodotti.get(m.codiceprod));
							uscita = false;
							break;
						} else {
							System.out.println("movimento inserito non corretto, reinserire i dati");
							uscSba = true;
						}
					}
				} while (uscSba == true);
				// sessione verifica giacenza
			} else if (giacenza == true) {
				do {
					giacSba = false;
					System.out.println("inserire il codice del prodotto del quale si vuole verificare la giacenza:");
					m.codiceprod = sc.nextLine();
					if (prodotti.containsKey(m.codiceprod)) {
						System.out.println("la giacenza in magazzino dell'articolo " + prodotti.get(m.codiceprod)
								+ " è di " + giacenzaProd.get(m.codiceprod));
						giacenza = false;
					} else {
						System.out.println("codice prodotto non valido, reinserire");
						giacSba = true;
					}
				} while (giacSba = true);
			} else if (ricerca == true) {
				do {
					sceltaSbagliata = false;
					System.out.println("vuoi ricercare tra i clienti(1) o tra i fornitori(2)?");
					risp = sc.nextLine();
					if (risp.equals("1")) {
						System.out.println("Inserisci il codice cliente:");
						m.codiceCliente = sc.nextLine();
						if (movimentiCliente.containsKey(m.codiceCliente)) {
							System.out.println(
									"i movimenti relativi al cliente " + clienti.get(m.codiceCliente) + " sono:");
							for (int j = 0; j < movimentiCliente.get(m.codiceCliente).size(); j++) {
								System.out.println("il movimento: " + movimentiCliente.get(m.codiceCliente).get(j));

							}
						}

					} else if (risposta.equals("2")) {
						System.out.println("Inserisci il codice fornitore:");
						m.codiceForn = sc.nextLine();
						if (movimentiCliente.containsKey(m.codiceForn)) {
							System.out.println(
									"i movimenti relativi al fornitore " + fornitori.get(m.codiceForn) + " sono:");
							for (int j = 0; j < movimentiFornitore.size(); j++) {
								System.out.println("il movimento: " + movimentiFornitore.get(m.codiceForn).get(j));
							}
						}
					} else {
						System.out.println("inserire un valore valido");
						sceltaSbagliata = true;
					}
				} while (sceltaSbagliata == true);
				ricerca = false;
			}
			// richiesta restart
			System.out.println("desideri fare un'altra operazione?");
			rispostaRestart = sc.nextLine();
			if (rispostaRestart.equalsIgnoreCase("no")) {
				System.out.println("arrivederci");
			}
		} while (rispostaRestart.equalsIgnoreCase("si") || rispostaRestart.equalsIgnoreCase("sì"));

	}// fine main

}
