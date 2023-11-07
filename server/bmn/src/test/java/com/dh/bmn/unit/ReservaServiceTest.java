package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.*;
import com.dh.bmn.dtos.responses.*;
import com.dh.bmn.entity.*;
import com.dh.bmn.exceptions.IllegalDateException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IReservaRepository;
import com.dh.bmn.security.user.Rol;
import com.dh.bmn.services.impl.BicicletaService;
import com.dh.bmn.services.impl.ReservaService;
import com.dh.bmn.services.impl.UsuarioService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private IReservaRepository reservaRepository;
    @InjectMocks
    private ReservaService reservaService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private BicicletaService bicicletaService;

    @BeforeEach
    public void setup() {
        reservaRepository = mock(IReservaRepository.class);
        bicicletaService = mock(BicicletaService.class);
        usuarioService = mock(UsuarioService.class);
        reservaService = new ReservaService(reservaRepository, usuarioService, bicicletaService);
    }

    @Test
    public void crearReserva() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaBicicletaList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaBicicletaList = List.of(caracteristicaBicicletaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicaBicicletaList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 12, 23), LocalDate.of(2023, 12, 24));
        //Act
        reservaService.crear(reservaRequestDto);

        // Asserts
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    public void crearReservaThrowsResourceAlreadyExistsException() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaBicicletaList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaBicicletaList = List.of(caracteristicaBicicletaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicaBicicletaList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        Usuario usuarioEntity = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Reserva reservaEntity = new Reserva(1L, usuarioEntity, bicicletaEntity, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        //Act
        when(reservaRepository.findByBicicletaAndFechaInicioAndFechaFin(reservaRequestDto.getBicicleta().getBicicletaId(), reservaRequestDto.getFechaInicio(), reservaRequestDto.getFechaFin())).thenReturn(Optional.of(reservaEntity));

        // Asserts
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            reservaService.crear(reservaRequestDto);
        });
    }
    @Test
    public void obtenerReservaPorId() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Reserva reserva = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        ImagenResponseDto imagenResponseDto = new ImagenResponseDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<ImagenResponseDto> imagenesResponse = List.of(imagenResponseDto);
        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", imagenResponseDto);
        List<CategoriaBicicletaResponseDto> categoriaResponseList = List.of(categoriaBicicletaResponseDto);
        CaracteristicaBicicletaResponseDto caracteristicaResponse = new CaracteristicaBicicletaResponseDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaResponseDto> caracteristicaResponseList = List.of(caracteristicaResponse);
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaResponseList, imagenesResponse, caracteristicaResponseList);
        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);
        ReservaResponseDto reservaEsperada = new ReservaResponseDto(1L, usuarioEsperado, bicicletaEsperada, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        //Act
        Mockito.when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        ReservaResponseDto reservaResponseDto = reservaService.buscarPorId(1L);

        // Asserts
        Assertions.assertEquals(reservaEsperada.getReservaId(), reservaResponseDto.getReservaId());
    }

    @Test
    public void obtenerReservaPorIdThrowsResourceNotFoundException() {
        Mockito.when(reservaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservaService.buscarPorId(1L);
        });
    }

    @Test
    public void obtenerTodasLasReservas() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reservaEntity);
        ImagenResponseDto imagenResponseDto = new ImagenResponseDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", imagenResponseDto);
        List<ImagenResponseDto> imagenesResponse = List.of(imagenResponseDto);
        List<CategoriaBicicletaResponseDto> categoriaResponseList = List.of(categoriaBicicletaResponseDto);
        CaracteristicaBicicletaResponseDto caracteristicaResponse = new CaracteristicaBicicletaResponseDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaResponseDto> caracteristicaResponseList = List.of(caracteristicaResponse);
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaResponseList, imagenesResponse, caracteristicaResponseList);
        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);
        ReservaResponseDto reservaEsperada = new ReservaResponseDto(1L, usuarioEsperado, bicicletaEsperada, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        List<ReservaResponseDto> reservaResponseLista = new ArrayList<>();
        reservaResponseLista.add(reservaEsperada);

        //Act
        Mockito.when(reservaRepository.findAll()).thenReturn(reservas);
        List<ReservaResponseDto> reservaLista = reservaService.listarTodos();

        // Asserts
        Assertions.assertEquals(reservaResponseLista.size(), reservaLista.size());
    }

    @Test
    public void listarTodasReservasThrowsResourceNotFoundException() {

        Mockito.when(reservaRepository.findAll()).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservaService.listarTodos();
        });
    }

    @Test
    public void eliminarReserva() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        //Act
        Mockito.when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaEntity));
        doNothing().when(reservaRepository).delete(reservaEntity);
        reservaService.borrarPorId(1L);

        // Asserts
        verify(reservaRepository, times(1)).delete(reservaEntity);
    }

    @Test
    public void eliminarReservaThrowsResourceNotFoundException() {

        Mockito.when(reservaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservaService.borrarPorId(1L);
        });
    }

    @Test
    public void actualizarReserva() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaRequestList = List.of(caracteristicaBicicletaRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaRequestList, imagenes,caracteristicaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 12, 23), LocalDate.of(2023, 12, 24));
        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 12, 23), LocalDate.of(2023, 12, 24));

        //Act
        Mockito.when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaEntity));
        reservaService.actualizar(reservaRequestDto);

        // Asserts
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    public void actualizarReservaThrowsResourceNotFoundException() throws MalformedURLException {

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaList = List.of(caracteristicaBicicletaRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        Mockito.when(reservaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservaService.actualizar(reservaRequestDto);
        });
    }

    @Test
    public void obtenerReservasPorIdUsuario() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reservaEntity);
        ImagenResponseDto imagenResponseDto = new ImagenResponseDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", imagenResponseDto);
        List<ImagenResponseDto> imagenesResponse = List.of(imagenResponseDto);
        List<CategoriaBicicletaResponseDto> categoriaResponseList = List.of(categoriaBicicletaResponseDto);
        CaracteristicaBicicletaResponseDto caracteristicaBicicletaResponseDto = new CaracteristicaBicicletaResponseDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaResponseDto> caracteristicResponseList = List.of(caracteristicaBicicletaResponseDto);
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaResponseList, imagenesResponse, caracteristicResponseList);
        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);
        ReservaResponseDto reservaEsperada = new ReservaResponseDto(1L,usuarioEsperado, bicicletaEsperada,LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        List<ReservaResponseDto> reservaResponseLista = new ArrayList<>();
        reservaResponseLista.add(reservaEsperada);

        //Act
        Mockito.when(reservaRepository.findReservasByUsuarioId(1L)).thenReturn(reservas);
        List<ReservaResponseDto> reservaLista = reservaService.obtenerReservasPorUsuario(1L);

        // Asserts
        Assertions.assertEquals(reservaResponseLista.size(), reservaLista.size());
    }


    @Test
    public void obtenerReservasPorUsuarioThrowsResourceNotFoundException() {

        Mockito.when(reservaRepository.findReservasByUsuarioId(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservaService.obtenerReservasPorUsuario(1L);
        });
    }

    @Test
    public void validarSiExisteReservaPreviaEnMetodoCrearThrowsResourceAlreadyExistsException() throws MalformedURLException {
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaRequestList = List.of(caracteristicaBicicletaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaRequestList, imagenes, caracteristicaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        Mockito.when(reservaRepository.findByBicicletaAndFechaInicioAndFechaFin(1L, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03))).thenReturn(Optional.of(reservaEntity));

        ResourceAlreadyExistsException exception = Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            reservaService.crear(reservaRequestDto);
        });

        Assertions.assertEquals("Ya existe una reserva en esa fecha para la bicicleta especificada", exception.getMessage());
    }

    @Test
    public void validarSiExisteReservaPreviaEnMetodoActualizarThrowsResourceAlreadyExistsException() throws MalformedURLException {
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaRequestList = List.of(caracteristicaBicicletaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaRequestList, imagenes, caracteristicaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 11, 02), LocalDate.of(2023, 11, 03));
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 11, 02), LocalDate.of(2023, 11, 03));
        Mockito.when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaEntity));
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reservaEntity);

        Mockito.when(reservaRepository.findReservasByBicicletaId(1L)).thenReturn(reservas);
        Mockito.when(reservaRepository.findByBicicletaAndFechaInicioAndFechaFin(1L, LocalDate.of(2023, 11, 02), LocalDate.of(2023, 11, 03))).thenReturn(Optional.of(reservaEntity));
        ResourceAlreadyExistsException exception = Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            reservaService.actualizar(reservaRequestDto);
        });

        Assertions.assertEquals("Ya existe una reserva en esa fecha para la bicicleta especificada", exception.getMessage());
    }

    @Test
    public void validarFechaInicioMenorAActualValidaThrowsIllegalDateException() throws MalformedURLException {

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaRequestList = List.of(caracteristicaBicicletaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaRequestList, imagenes, caracteristicaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
       IllegalDateException exception = Assertions.assertThrows(IllegalDateException .class, () -> {
            reservaService.crear(reservaRequestDto);
        });

        Assertions.assertEquals("La fecha de inicio no puede ser menor a la actual", exception.getMessage());
    }

    @Test
    public void validarFechaFinMenorAFechaInicioThrowsIllegalDateException() throws MalformedURLException {
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaRequestList = List.of(caracteristicaBicicletaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaRequestList, imagenes, caracteristicaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 11, 19), LocalDate.of(2023, 11, 10));
        IllegalDateException exception = Assertions.assertThrows(IllegalDateException .class, () -> {
            reservaService.crear(reservaRequestDto);
        });

        Assertions.assertEquals("La fecha de fin no puede ser menor a la fecha de inicio", exception.getMessage());
    }
}
