package com.overstained.quote.io.csv;

import java.io.IOError;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.overstained.quote.io.exceptions.CSVFileNotAccessibleException;
import com.overstained.quote.io.exceptions.CSVFileNotFoundException;
import com.overstained.quote.io.exceptions.CSVFileNotProvidedException;

public class DefaultFilePathResolver implements FilePathResolver {

	@Override
	public Path resolve(String filePath) {
		if(filePath == null || filePath.trim().isEmpty()) {
			throw new CSVFileNotProvidedException();
		}
		try {
			return Paths.get(filePath).toAbsolutePath();
		} catch(InvalidPathException ipe) {
			throw new CSVFileNotFoundException(filePath);
		} catch(IOError ioe) {
			throw new CSVFileNotAccessibleException(filePath);
		}
	}
}
