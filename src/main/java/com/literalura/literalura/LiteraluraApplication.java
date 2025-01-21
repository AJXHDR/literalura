package com.literalura.literalura;

import com.literalura.api.BookApiClient;

import java.util.*;

public class LiteraluraApplication {
	// Lista para almacenar los libros registrados
	private static Set<Book> registeredBooks = new HashSet<>();
	// Lista para almacenar los autores registrados
	private static Map<String, Author> authors = new HashMap<>();
	// Mapa de códigos de idioma a nombre completo
	private static Map<String, String> languageMap = new HashMap<>();

	static {
		languageMap.put("en", "Inglés");
		languageMap.put("es", "Español");
		languageMap.put("fr", "Francés");
		languageMap.put("de", "Alemán");
		languageMap.put("it", "Italiano");
		languageMap.put("pt", "Portugués");
		languageMap.put("hun", "Húngaro");
	}

	public static void main(String[] args) {
		BookApiClient client = new BookApiClient();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			showMenu();
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1:
					searchAndRegisterBook(scanner, client);
					break;
				case 2:
					listBooks();
					break;
				case 3:
					listAuthors();
					break;
				case 4:
					listLivingAuthorsInYear(scanner);
					break;
				case 5:
					listBooksByLanguage(scanner);
					break;
				case 0:
					System.out.println("Saliendo...");
					return;
				default:
					System.out.println("Opción no válida.");
			}
		}
	}

	private static void showMenu() {
		System.out.println("\n1. Buscar libro por título");
		System.out.println("2. Listar libros registrados");
		System.out.println("3. Listar autores registrados");
		System.out.println("4. Listar autores vivos en determinado año");
		System.out.println("5. Listar libros por idioma");
		System.out.println("0. Salir");
		System.out.print("Elige una opción: ");
	}

	// Buscar y registrar libro
	private static void searchAndRegisterBook(Scanner scanner, BookApiClient client) {
		System.out.print("Introduce el título del libro: ");
		String title = scanner.nextLine();

		// Buscar libro por título
		Book book = client.getBookByTitle(title);
		if (book != null) {
			// Verificar si el libro ya está registrado
			if (registeredBooks.contains(book)) {
				System.out.println("No se puede registrar el mismo libro más de una vez.");
			} else {
				registeredBooks.add(book);
				addAuthor(book);  // Registrar autor también
				System.out.println(book);  // Mostrar detalles del libro registrado
			}
		} else {
			System.out.println("Libro no encontrado.");
		}
	}

	// Agregar autor a la lista de autores
	private static void addAuthor(Book book) {
		String authorName = book.getAuthor();
		if (!authors.containsKey(authorName)) {
			// Aquí se añaden los datos del autor (sin detalles de nacimiento/fallecimiento por ahora)
			authors.put(authorName, new Author(authorName, 0, 0, new ArrayList<>()));
		}
		authors.get(authorName).getBooks().add(book);  // Añadir el libro al autor
	}

	// Listar todos los libros registrados
	private static void listBooks() {
		if (registeredBooks.isEmpty()) {
			System.out.println("No hay libros registrados.");
		} else {
			registeredBooks.forEach(book -> {
				String languageFull = languageMap.getOrDefault(book.getLanguage().toLowerCase(), "Desconocido");
				System.out.println("----- LIBRO -----");
				System.out.println("Título: " + book.getTitle());
				System.out.println("Autor: " + book.getAuthor());
				System.out.println("Idioma: " + languageFull);
				System.out.println("Número de descargas: " + book.getDownloads());
				System.out.println("--------------------");
			});
		}
	}

	// Listar autores registrados
	private static void listAuthors() {
		if (authors.isEmpty()) {
			System.out.println("No hay autores registrados.");
		} else {
			authors.forEach((name, author) -> System.out.println(author));
		}
	}

	// Listar autores vivos en determinado año
	private static void listLivingAuthorsInYear(Scanner scanner) {
		System.out.print("Introduce el año: ");
		int year = scanner.nextInt();
		scanner.nextLine();  // Consumir el salto de línea

		boolean found = false;
		for (Author author : authors.values()) {
			if (author.getBirthYear() <= year && (author.getDeathYear() == 0 || author.getDeathYear() >= year)) {
				System.out.println(author);
				found = true;
			}
		}
		if (!found) {
			System.out.println("No se encontraron autores vivos en ese año.");
		}
	}

	// Listar libros por idioma
	private static void listBooksByLanguage(Scanner scanner) {
		System.out.print("Introduce el idioma (por ejemplo, 'es' para español): ");
		String language = scanner.nextLine().toLowerCase();

		boolean found = false;
		for (Book book : registeredBooks) {
			if (book.getLanguage().toLowerCase().startsWith(language)) {
				String languageFull = languageMap.getOrDefault(book.getLanguage().toLowerCase(), "Desconocido");
				System.out.println(book);
				System.out.println("Idioma: " + languageFull);
				found = true;
			}
		}
		if (!found) {
			System.out.println("No se encontraron libros en ese idioma.");
		}
	}
}