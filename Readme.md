# Google Distance 

use the following request to collect data for a period of time ahead. 
replace API_KEY and OUTPUT_DIRECTORY with your own choice

          `var request = new DataCollectionRequest()
                    .origin("Colombo, Sri Lanka")
                    .destination("Kandy, Sri Lanka")
                    .travelMode(TravelMode.DRIVING)
                    .startTime(LocalDateTime.of(2021, 10, 16, 8, 30)) // from 2021-10-16 8:30
                    .endTime(LocalDateTime.of(2021, 10, 16, 10, 30)) // to 2021-10-16 10:30
                    .zoneOffset(ZoneOffset.ofHoursMinutes(5, 30)) // Sri Lanka
                    .frequencyInSeconds(300); // 5 Minutes


            new APIConsumer(API_KEY).getDistanceMatrixAsync(request, d -> new FileWriter(OUTPUT_DIRECTORY).writeResults(d));`
