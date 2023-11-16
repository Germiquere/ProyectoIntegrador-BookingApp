package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.*;
import com.dh.bmn.dtos.responses.*;
import com.dh.bmn.entity.*;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.ICaracteristicaBicicletaRepository;
import com.dh.bmn.repositories.ICategoriaBicicletaRepository;
import com.dh.bmn.repositories.IPoliticaRepository;
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
    private ICategoriaBicicletaRepository categoriaBicicletaRepository;

    @Mock
    private ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository;

    @Mock
    private IPoliticaRepository politicaRepository;

    @Mock
    private S3Service s3Service;
    @InjectMocks
    private BicicletaService bicicletaService;

    @BeforeEach
    public void setup() {
        bicicletaRepository = mock(IBicicletaRepository.class);
        categoriaBicicletaRepository = mock(ICategoriaBicicletaRepository.class);
        caracteristicaBicicletaRepository = mock(ICaracteristicaBicicletaRepository.class);
        politicaRepository = mock(IPoliticaRepository.class);
        s3Service = mock(S3Service.class);
        //bicicletaService = new BicicletaService(bicicletaRepository, s3Service, caracteristicaBicicletaRepository, categoriaBicicletaRepository, politicaRepository);
    }

    /*@Test
    public void crearBicicleta() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaBicicletaList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaBicicletaList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicaBicicletaList, politicaBicicletaList);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        //Act

        Mockito.when(categoriaBicicletaRepository.findById(1L)).thenReturn(Optional.of(categoriaBicicleta));
        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenReturn(Optional.of(caracteristicaBicicleta));
        Mockito.when(politicaRepository.findById(1L)).thenReturn(Optional.of(politica));


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
        List<CategoriaBicicletaRequestDto> categoriaBicicletaList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaBicicletaList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicaBicicletaList, politicaBicicletaList);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categorias = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicas = List.of(caracteristicaBicicleta);
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        List<Politica> politicaBicicletaEntityList = List.of(politica);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categorias, imagenes, caracteristicas, politicaBicicletaEntityList);

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
        List<CategoriaBicicleta> categoriaBicicletaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaBicicletaList = List.of(caracteristicaBicicleta);
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        List<Politica> politicaBicicletaEntityList = List.of(politica);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicaBicicletaList, politicaBicicletaEntityList);
        List<ImagenResponseDto> imagenesResponse = List.of(imagenResponseDto);
        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", imagenResponseDto);
        List<CategoriaBicicletaResponseDto> categoriaBicicletaResponseDtoList = List.of(categoriaBicicletaResponseDto);
        CaracteristicaBicicletaResponseDto caracteristicaBicicletaResponseDto = new CaracteristicaBicicletaResponseDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaResponseDto> caracteristicasResponse = List.of(caracteristicaBicicletaResponseDto);
        PoliticaResponseDto politicaResponseDto = new PoliticaResponseDto(1L, "Politica", "Descripcion politica");
        List<PoliticaResponseDto> politicaBicicletaResponseList = List.of(politicaResponseDto);
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDtoList, imagenesResponse, caracteristicasResponse, politicaBicicletaResponseList);

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
        List<CategoriaBicicleta> categoriaBicicletaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicasList = List.of(caracteristicaBicicleta);
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        List<Politica> politicaBicicletaEntityList = List.of(politica);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicasList, politicaBicicletaEntityList);
        List<Bicicleta> bicicletas = new ArrayList<>();
        bicicletas.add(bicicletaEntity);
        ImagenResponseDto imagenResponseDto = new ImagenResponseDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<ImagenResponseDto> imagenesResponse = List.of(imagenResponseDto);
        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", imagenResponseDto);
        List <CategoriaBicicletaResponseDto> categoriaBicicletaResponseDtoList = List.of(categoriaBicicletaResponseDto);
        CaracteristicaBicicletaResponseDto caracteristicaBicicletaResponseDto = new CaracteristicaBicicletaResponseDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaResponseDto> caracteristicasResponseList = List.of(caracteristicaBicicletaResponseDto);
        PoliticaResponseDto politicaResponseDto = new PoliticaResponseDto(1L, "Politica", "Descripcion politica");
        List<PoliticaResponseDto> politicaBicicletaResponseList = List.of(politicaResponseDto);
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDtoList, imagenesResponse, caracteristicasResponseList, politicaBicicletaResponseList);
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
        List<CategoriaBicicleta> categoriaBicicletaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicasList = List.of(caracteristicaBicicleta);
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        List<Politica> politicaBicicletaEntityList = List.of(politica);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicasList, politicaBicicletaEntityList);

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
        List<CategoriaBicicleta> categoriaBicicletaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");
        List<CaracteristicaBicicleta> caracteristicasList = List.of(caracteristicaBicicleta);
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        List<Politica> politicaBicicletaEntityList = List.of(politica);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicasList, politicaBicicletaEntityList);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaBicicletaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicasRequestList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestList, imagenes, caracteristicasRequestList, politicaBicicletaList);

        //Act
        Mockito.when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicletaEntity));
        Mockito.when(categoriaBicicletaRepository.findById(1L)).thenReturn(Optional.of(categoriaBicicleta));
        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenReturn(Optional.of(caracteristicaBicicleta));
        Mockito.when(politicaRepository.findById(1L)).thenReturn(Optional.of(politica));

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
        List<CategoriaBicicletaRequestDto> categoriaBicicletaRequestDtoList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicasRequestList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDtoList, imagenes, caracteristicasRequestList, politicaBicicletaList);
        Mockito.when(bicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.actualizar(bicicletaRequestDto);
        });
    }

    @Test
    public void testValidarListaImagenesVaciaThrowsRequestValidationException() throws MalformedURLException {
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaBicicletaRequestDtoList = List.of(categoriaBicicletaRequestDto);
        List<Imagen> imagenes = List.of();
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicasRequestList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "bike", "ideal para montaña", 34567, categoriaBicicletaRequestDtoList, imagenes, caracteristicasRequestList, politicaBicicletaList);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        Mockito.when(categoriaBicicletaRepository.findById(1L)).thenReturn(Optional.of(categoriaBicicleta));
        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenReturn(Optional.of(caracteristicaBicicleta));
        Mockito.when(politicaRepository.findById(1L)).thenReturn(Optional.of(politica));

        RequestValidationException exception = Assertions.assertThrows(RequestValidationException.class, () -> {
            bicicletaService.crear(bicicletaRequestDto);
        });

        Assertions.assertEquals("La lista de imagenes no puede estar vacia", exception.getMessage());
    }*/
}
