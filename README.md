<!-- Java SpringBoot Backend -->
## Java SpringBoot Backend

### Built With

This section should list any major frameworks/libraries used to my project.

#### Java
#### SpringBoot
#### HTML

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started


- Health Check
- REST Controller
- ERROR  Adviser
- Exception Handler
- Panding Topic(Upload File)
      
      
      @PostMapping("/upload-profile")
          public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws BaseException {
              String response = business.uploadProfilePicture(file);
              return ResponseEntity.ok(response);
          }
     
   
      public class FileException extends BaseException {

          public FileException(String code) {
              super("file." + code);
          }

          public static FileException fileNull() {
              return new FileException("null");
          }

          public static FileException fileMaxSize() {
              return new FileException("max.size");
          }

          public static FileException unsupported() {
              return new FileException("unsupported.file.type");
          }

      }
    
            public String uploadProfilePicture(MultipartFile file) throws BaseException {
              // validate file
              if (file == null) {
                  // throw error
                  throw FileException.fileNull();
              }

              // validate size
              if (file.getSize() > 1048576 * 2) {
                  // throw error
                  throw FileException.fileMaxSize();
              }

              String contentType = file.getContentType();
              if (contentType == null) {
                  // throw error
                  throw FileException.unsupported();
              }

              List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
              if (!supportedTypes.contains(contentType)) {
                  // throw error (unsupport)
                  throw FileException.unsupported();
              }

              // TODO: upload file File Storage (AWS S3, etc...)
              try {
                  byte[] bytes = file.getBytes();
              } catch (IOException e) {
                  e.printStackTrace();
              }

              return "";
          }
         

- Database (PostgreSQl) run in Dockker < DB 1 Table >
- Database (Foreign key : OnetoOne, OnetoMany ) < Add Datable 1-2 Tabable connection Old DB>

![DB+Docker](https://user-images.githubusercontent.com/106970646/184547210-ed45ba34-7331-4921-96c8-81fe60073a58.png)

- Security 
- Unit Test

![Test](https://user-images.githubusercontent.com/106970646/184548522-a9922608-7588-4bda-b877-a1499c5a0419.png)

- JWT 
      -confic
            ```
            <dependency>
                <groupId>com.auth0</groupId>
                <artifactId>java-jwt</artifactId>
                <version>4.0.0</version>
            </dependency>
            ````

![JWT](https://user-images.githubusercontent.com/106970646/184547126-a235ab92-b2b8-4546-8969-160d345b30f3.jpg)

- Email (ActiveUserEmail)

![CB165FB7-02D4-45E0-B267-9BE213D3CCD6](https://user-images.githubusercontent.com/106970646/184545855-8a9faf94-1fb0-4f83-9bc4-08ebc9cb7aa5.jpg)

- Job Scheudler (Working on setTime)

        
                package com.training.backend.schedule;

                import com.training.backend.service.UserService;
                import lombok.extern.log4j.Log4j2;
                import org.springframework.scheduling.annotation.Scheduled;
                import org.springframework.stereotype.Service;

                @Service
                @Log4j2
                public class UserSchedule {

                    private final UserService userService;

                    public UserSchedule(UserService userService) {
                        this.userService = userService;
                    }

                    // Schedule Note
                    // 1 => second
                    // 2 => minute
                    // 3 => hour
                    // 4 => day
                    // 5 => month
                    // 6 => year

                    /**
                     * Every minute (UTC Time)
                     */
                    @Scheduled(cron = "0 * * * * *")
                    public void testEveryMinute() {
                        log.info("Hello, What's up?");
                    }

                    /**
                     * Everyday at 00:00 (UTC Time)
                     */
                    @Scheduled(cron = "0 0 0 * * *")
                    public void testEveryMidNight() {

                    }

                    /**
                     * Everyday at 10:50 AM (Thai Time)
                     */
                    @Scheduled(cron = "0 50 10 * * *", zone = "Asia/Bangkok")
                    public void testEverydayNineAM() {
                        log.info("Hey Hoo!!!");
                    }

                }
            

<p align="right">(<a href="#readme-top">back to top</a>)</p>

