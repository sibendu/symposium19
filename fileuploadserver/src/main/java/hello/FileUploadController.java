package hello;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.storage.StorageFileNotFoundException;
import hello.storage.StorageService;

@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile[] files, RedirectAttributes redirectAttributes) {
		String message = "Successfully uploaded file: ";
		for (int i = 0; i < files.length; i++) {
			storageService.store(files[i]);
			message = message + files[i].getOriginalFilename() + ", ";
		}
		
		redirectAttributes.addFlashAttribute("message",message);

		return "redirect:/";
	}

	@PostMapping("/upload")
	public ResponseEntity<String> processFileUpload(@RequestParam("file") MultipartFile file) {
		System.out.println("Upload request received");
		System.out.println(file);
		storageService.store(file);
		return new ResponseEntity<>("successfully uploaded", HttpStatus.OK);
	}

	@PostMapping("/uploadmultiple")
    public String processMultipleFileUpload(
    		@RequestParam("file1") MultipartFile file1,
    		@RequestParam("file2") MultipartFile file2,
    		@RequestParam("file3") MultipartFile file3,
    		RedirectAttributes redirectAttributes) {
    	System.out.println("Upload request received for multiple files");
    	
        storageService.store(file1);
        System.out.println("Stored file "+file1.getName());
        
        storageService.store(file2);
        System.out.println("Stored file "+file1.getName());
        
        storageService.store(file3);
        System.out.println("Stored file "+file3.getName());
        
        redirectAttributes.addFlashAttribute("message","Your files are uploaded successfully!");
        
        return "uploadMultipleForm";
    }

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));

		return "uploadForm";
	}
	
	@GetMapping("/uploadmultiple")
	public String listUploadedMultipleFiles(Model model) throws IOException {

		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));

		return "uploadMultipleForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
