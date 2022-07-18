package br.com.gluom.controller

import br.com.gluom.config.FileStorageConfig
import br.com.gluom.data.vo.v1.UploadFileResponseVO
import br.com.gluom.services.FileStorageService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.logging.Logger


@RestController
@RequestMapping("/api/v1/file")
@Tag(name = "File Endpoint", description = "Endpoints for Managing files")
class FileController {

    private val logger = Logger.getLogger(FileController::class.java.name)

    @Autowired
    private lateinit var fileStorageService: FileStorageService

    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile) : UploadFileResponseVO {
        logger.info("Uploading file...")

        val fileName = fileStorageService.storeFile(file)
        val fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/file/download/")
            .path(fileName)
            .toUriString()

        return UploadFileResponseVO(fileName, fileDownloadUrl, file.contentType!!, file.size)

        logger.info("File uploaded!")
    }

    @PostMapping("/uploadMultipleFiles")
    fun uploadFile(@RequestParam("files") files: Array<MultipartFile>) : List<UploadFileResponseVO> {
        logger.info("Uploading files...")

        val uploadFileResponseVOs = arrayListOf<UploadFileResponseVO>()

        for (file in files){
            var uploadFileResponseVO: UploadFileResponseVO = uploadFile(file)
            uploadFileResponseVOs.add(uploadFileResponseVO)
        }

        logger.info("Files uploaded!")

        return uploadFileResponseVOs
    }

    @GetMapping("/download/{fileName:.+}")
    fun uploadFile(@PathVariable fileName : String, request: HttpServletRequest) : ResponseEntity<Resource> {
        logger.info("Getting file $fileName")

        val resource = fileStorageService.loadFileAsResource(fileName)
        var contentType = ""

        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (ex: Exception) {
            logger.info("Could not determine file type.")
        }

        if (contentType.isBlank()) contentType = "application/octet-stream"

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, """attachment; filename="${resource.filename}"""")
            .body(resource)
    }
}