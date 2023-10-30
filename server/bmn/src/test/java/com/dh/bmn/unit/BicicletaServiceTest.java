package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dtos.requests.ImagenRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.dtos.responses.CategoriaBicicletaResponseDto;
import com.dh.bmn.dtos.responses.ImagenResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.CategoriaBicicleta;
import com.dh.bmn.entity.Imagen;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.services.impl.BicicletaService;
import com.dh.bmn.services.impl.S3Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BicicletaServiceTest {


    @Mock
    private IBicicletaRepository bicicletaRepository;

    @Mock
    private S3Service s3Service;
    @InjectMocks
    private BicicletaService bicicletaService;

    @BeforeEach
    public void setup() {

        bicicletaRepository = mock(IBicicletaRepository.class);
        s3Service = mock(S3Service.class);
        bicicletaService = new BicicletaService(bicicletaRepository, s3Service);
    }

    @Test
    public void crearBicicleta() throws MalformedURLException {
        //Arrange

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);

        //Act
        bicicletaService.crear(bicicletaRequestDto);

        // Asserts
        verify(bicicletaRepository, times(1)).save(any(Bicicleta.class));
    }

    @Test
    public void crearBicicletaThrowsResourceAlreadyExistsException() throws MalformedURLException {
        //Arrange
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));

        List<Imagen> imagenes = List.of(imagen);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);

        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        //Act
        when(bicicletaRepository.findByNombre(bicicletaRequestDto.getNombre())).thenReturn(Optional.of(bicicletaEntity));

        // Asserts
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            bicicletaService.crear(bicicletaRequestDto);
        });

    }


    @Test
    public void obtenerBicicletaPorId() throws MalformedURLException {
        //Arrange

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenResponseDto imagenResponseDto = new ImagenResponseDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        List<ImagenResponseDto> imagenesResponse = List.of(imagenResponseDto);
        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", imagenResponseDto);
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDto, imagenesResponse);

        //Act
        Mockito.when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicleta));
        BicicletaResponseDto bicicletaResponseDto = bicicletaService.buscarPorId(1L);

        // Asserts
        Assertions.assertEquals(bicicletaEsperada.getBicicletaId(), bicicletaResponseDto.getBicicletaId());

    }

    @Test
    public void obtenerBicicletaPorIdThrowsResourceNotFoundException() {
        Mockito.when(bicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.buscarPorId(1L);
        });
    }

    @Test
    public void obtenerTodasLasBicicletas() throws MalformedURLException {
        //Arrange

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        List<Bicicleta> bicicletas = new ArrayList<>();
        bicicletas.add(bicicletaEntity);


        ImagenResponseDto imagenResponseDto = new ImagenResponseDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<ImagenResponseDto> imagenesResponse = List.of(imagenResponseDto);
        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", imagenResponseDto);
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDto, imagenesResponse);
        List<BicicletaResponseDto> bicicletaResponseLista = new ArrayList<>();
        bicicletaResponseLista.add(bicicletaEsperada);

        //Act
        Mockito.when(bicicletaRepository.findAll()).thenReturn(bicicletas);
        List<BicicletaResponseDto> bicicletaLista = bicicletaService.listarTodos();

        // Asserts
        Assertions.assertEquals(bicicletaResponseLista.size(), bicicletaLista.size());

    }

    @Test
    public void listarTodasBicicletasThrowsResourceNotFoundException() {

        Mockito.when(bicicletaRepository.findAll()).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.listarTodos();
        });

    }

    @Test
    public void eliminarBicicleta() throws MalformedURLException {
        //Arrange

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        //Act
        Mockito.when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicletaEntity));
        doNothing().when(bicicletaRepository).delete(bicicletaEntity);

        bicicletaService.borrarPorId(1L);

        // Asserts
        verify(bicicletaRepository, times(1)).delete(bicicletaEntity);
    }

    @Test
    public void eliminarBicicletaThrowsResourceNotFoundException() {

        Mockito.when(bicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.borrarPorId(1L);
        });
    }

    @Test
    public void actualizarBicicleta() throws MalformedURLException {
        //Arrange

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);

        //Act
        Mockito.when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicletaEntity));

        bicicletaService.actualizar(bicicletaRequestDto);

        // Asserts
        verify(bicicletaRepository, times(1)).save(any(Bicicleta.class));
    }

    @Test
    public void actualizarBicicletaThrowsResourceNotFoundException() throws MalformedURLException {

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);

        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);

        Mockito.when(bicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.actualizar(bicicletaRequestDto);
        });
    }

    @Test
    public void testValidarListaImagenesVaciaThrowsRequestValidationException() throws MalformedURLException {

        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);

        List<Imagen> imagenes = List.of();
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "bike", "ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);

        RequestValidationException exception = Assertions.assertThrows(RequestValidationException.class, () -> {
            bicicletaService.crear(bicicletaRequestDto);
        });

        Assertions.assertEquals("La lista de imagenes no puede estar vacia", exception.getMessage());
    }
}
