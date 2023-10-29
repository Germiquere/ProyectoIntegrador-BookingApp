package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dtos.requests.ReservaRequestDto;
import com.dh.bmn.dtos.requests.UsuarioRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.dtos.responses.CategoriaBicicletaResponseDto;
import com.dh.bmn.dtos.responses.ReservaResponseDto;
import com.dh.bmn.dtos.responses.UsuarioResponseDto;
import com.dh.bmn.embeddable.Imagen;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.CategoriaBicicleta;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.entity.Usuario;
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
    public void crearReserva() {
        //Arrange
        Imagen imagen = new Imagen("https://www.bicidemontaña.com");
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        //Act
        reservaService.crear(reservaRequestDto);

        // Asserts
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    public void crearReservaThrowsResourceAlreadyExistsException() {
        //Arrange

        Imagen imagen = new Imagen("https://www.bicidemontaña.com");
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);
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
    public void obtenerReservaPorId() {
        //Arrange

        Imagen imagen = new Imagen("https://www.bicidemontaña.com");
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        Reserva reserva = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDto, imagenes);


        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);
        ReservaResponseDto reservaEsperada = new ReservaResponseDto(1L,usuarioEsperado, bicicletaEsperada,LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

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
    public void obtenerTodasLasReservas() {
        //Arrange

        Imagen imagen = new Imagen("https://www.bicidemontaña.com");
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reservaEntity);

        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDto, imagenes);

        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);

        ReservaResponseDto reservaEsperada = new ReservaResponseDto(1L,usuarioEsperado, bicicletaEsperada,LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

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
    public void eliminarReserva() {
        //Arrange

        Imagen imagen = new Imagen("https://www.bicidemontaña.com");
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

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
    public void actualizarReserva() {
        //Arrange

        Imagen imagen = new Imagen("https://www.bicidemontaña.com");
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);

        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        //Act
        Mockito.when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaEntity));

        reservaService.actualizar(reservaRequestDto);

        // Asserts
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    public void actualizarReservaThrowsResourceNotFoundException() {


        Imagen imagen = new Imagen("https://www.bicidemontaña.com");
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto, imagenes);

        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        Mockito.when(reservaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reservaService.actualizar(reservaRequestDto);
        });
    }

    @Test
    public void obtenerReservasPorIdUsuario(){
        //Arrange

        Imagen imagen = new Imagen("https://www.bicidemontaña.com");
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta, imagenes);

        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        Reserva reservaEntity = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));

        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reservaEntity);

        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDto, imagenes);

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
}
