package com.overstained.quote.io.csv;

import java.nio.file.Path;

public interface FilePathResolver {
	Path resolve(String filePath);
}
