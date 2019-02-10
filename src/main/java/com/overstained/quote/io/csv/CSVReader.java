package com.overstained.quote.io.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.function.Consumer;

import com.overstained.quote.io.exceptions.CSVEmptyFileException;
import com.overstained.quote.io.exceptions.CSVFileNotFoundException;
import com.overstained.quote.io.exceptions.CSVInvalidFileException;
import com.overstained.quote.utils.MutableBoolean;

public class CSVReader {
	private Path path;
	private String charset;

	public CSVReader(Path path, String charset) {
		this.path = path;
		this.charset = charset;
	}

	public void readFile(Consumer<String> headerCallback, Consumer<String> dataCallback) {
		long numberOfLines = 0;
		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName(charset))) {
			MutableBoolean firstLine = new MutableBoolean();
			numberOfLines = bufferedReader.lines().peek(line -> {
				if (firstLine.getValue()) {
					firstLine.setValue(false);
					headerCallback.accept(line);
				} else {
					dataCallback.accept(line);
				}
			}).count();
		} catch (FileNotFoundException | NoSuchFileException ne) {
			throw new CSVFileNotFoundException(path.toString());
		} catch (IOException ioe) {
			throw new CSVInvalidFileException(path.toString());
		}
		if (numberOfLines == 0) {
			throw new CSVEmptyFileException(path.toString());
		}
	}
}
