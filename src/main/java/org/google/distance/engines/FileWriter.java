package org.google.distance.engines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

public class FileWriter {
    private static Logger logger = LoggerFactory.getLogger(FileWriter.class);
    private DateTimeFormatter dateTimeFormatter;
    private String outputDirectory;

    public FileWriter(String outputDirectory) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.outputDirectory = outputDirectory;

        File file = new File(outputDirectory);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void writeResults(DataCollectionResponse response) {
        try {
            var fileName = new StringBuilder()
                    .append(response.getOrigin()).append("_")
                    .append(response.getDestination()).append("_")
                    .append(".csv").toString()
                    .replace(":", "_")
                    .replace(",", "_")
                    .replaceAll("\\s", "_")
                    .toLowerCase(Locale.ROOT);

            File file = new File(outputDirectory, fileName);


            if (!file.exists() && file.createNewFile()) {
                String headers = "Time (Human Readable),Time (Epoch),Zone,Distance (Human Readable),Distance (m),Duration (Human Readable),Duration (s),Duration In Traffic (Human Readable) Duration In Traffic (s)\n";
                writeLine(outputDirectory, fileName, headers);

            }


            Arrays.stream(response.getResult().rows).forEach(r -> {

                Arrays.stream(r.elements).sequential().forEach(element -> {
                    var line = new StringBuilder()
                            .append(response.getDepartureDate().format(dateTimeFormatter)).append(",")
                            .append(response.getDepartureDate().toEpochSecond(response.getZoneOffset())).append(",")
                            .append(response.getZoneOffset()).append(",")
                            .append(element.distance.humanReadable).append(",")
                            .append(element.distance.inMeters).append(",")
                            .append(element.duration.humanReadable).append(",")
                            .append(element.duration.inSeconds).append(",")
                            .append(element.durationInTraffic.humanReadable).append(",")
                            .append(element.durationInTraffic.inSeconds);
                    writeLine(outputDirectory, fileName, line.toString());

                });
                writeLine(outputDirectory, fileName, "\n");
            });

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void writeLine(String directory, String fileName, String line) {
        try {
            Files.write(
                    Paths.get(directory, fileName),
                    line.getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
