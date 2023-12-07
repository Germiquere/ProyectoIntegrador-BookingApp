package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.*;
import com.dh.bmn.dtos.responses.*;
import com.dh.bmn.entity.*;
import com.dh.bmn.exceptions.IllegalDateException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.IReservaRepository;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.repositories.IValoracionRepository;
import com.dh.bmn.security.user.Rol;
import com.dh.bmn.services.impl.BicicletaService;
import com.dh.bmn.services.impl.ReservaService;
import com.dh.bmn.services.impl.UsuarioService;
import com.dh.bmn.services.impl.ValoracionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = ValoracionServiceTest.class)
@ExtendWith(MockitoExtension.class)
public class ValoracionServiceTest {

    @Mock
    private IValoracionRepository valoracionRepository;

    @Mock
    private IReservaRepository reservaRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private BicicletaService bicicletaService;

    @InjectMocks
    private ValoracionService valoracionService;

    @BeforeEach
    public void setup() {
        valoracionRepository = mock(IValoracionRepository.class);
        reservaRepository = mock(IReservaRepository.class);
        usuarioRepository = mock(IUsuarioRepository.class);
        bicicletaService = mock(BicicletaService.class);
        valoracionService = new ValoracionService(valoracionRepository, reservaRepository,  usuarioRepository, bicicletaService);
    }

    @Test
    public void crearValoracion() throws MalformedURLException {

        //Arrange
        Reserva reservaMock = new Reserva();
        Optional<Reserva> reservaOptional = Optional.of(reservaMock);
        when(reservaRepository.findById(Mockito.anyLong())).thenReturn(reservaOptional);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("correo@ejemplo.com");
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("correo@ejemplo.com");
        Optional<Usuario> usuarioOptional = Optional.of(usuarioMock);
        when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioOptional);

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaBicicletaList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaBicicletaList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaList, imagenes, caracteristicaBicicletaList, politicaBicicletaList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 11, 23), LocalDate.of(2023, 11, 29));
        ValoracionRequestDto valoracionRequestDto = new ValoracionRequestDto(1L, reservaRequestDto, 5, "Bicicleta en excelente estado", bicicletaRequestDto);

        //when(reservaService.isConcluida(Mockito.any(Reserva.class))).thenReturn(true);

        //Act
        valoracionService.crear(valoracionRequestDto);

        // Asserts
        verify(valoracionRepository, times(1)).save(any(Valoracion.class));
    }


    @Test
    public void crearValoracionThrowsUsernameNotFoundException() {

        // Arrange
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("correo@ejemplo.com");

        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        ValoracionRequestDto valoracionRequestDto = new ValoracionRequestDto();

        // Act y Assert
        assertThrows(UsernameNotFoundException.class, () -> {
                    valoracionService.crear(valoracionRequestDto);
                },
                "Debería lanzarse una UsernameNotFoundException si el usuario no es encontrado."
        );
    }

    @Test
    public void crearValoracionThrowsResourceNotFoundException() {

        // Arrange
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("correo@ejemplo.com");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("correo@ejemplo.com");

        Optional<Usuario> usuarioOptional = Optional.of(usuarioMock);

        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioOptional);

        ValoracionRequestDto valoracionRequestDto = new ValoracionRequestDto();
        valoracionRequestDto.setReserva(null);

        // Act y Assert
        assertThrows(
                ResourceNotFoundException.class, () ->
                        valoracionService.crear(valoracionRequestDto),
                "Debería lanzarse una NullPointerException si reservaRequestDto es nulo."
        );
    }

//    @Test
//    public void crearValoracionThrowsIllegalDateException() {
//        //Arrange
//        BicicletaRequestDto bicicletaRequestDtoMock = Mockito.mock(BicicletaRequestDto.class);
//        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
//        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDtoMock, LocalDate.of(2023, 12, 23), LocalDate.of(2023, 12, 24));
//        Reserva reservaMock = new Reserva();
//        Optional<Reserva> reservaOptional = Optional.of(reservaMock);
//
//        Mockito.when(reservaRepository.findById(Mockito.anyLong())).thenReturn(reservaOptional);
//
//        Usuario usuarioMock = new Usuario();
//        usuarioMock.setEmail("correo@ejemplo.com");
//
//        Authentication authentication = Mockito.mock(Authentication.class);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Mockito.when(authentication.getName()).thenReturn("correo@ejemplo.com");
//
//        Optional<Usuario> usuarioOptional = Optional.of(usuarioMock);
//
//        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioOptional);
//
//        ValoracionRequestDto valoracionRequestDto = new ValoracionRequestDto(1L, reservaRequestDto, 5, "Bicicleta en excelente estado", bicicletaRequestDtoMock);
//
//        // Asserts
//        assertThrows(IllegalDateException.class, () -> {
//
//            valoracionService.crear(valoracionRequestDto);
//        });
//
//    }

    @Test
    public void crearValoracionResourceNotFoundException() throws MalformedURLException {

        //Arrange
        //when(reservaService.isConcluida(any(Reserva.class))).thenReturn(true);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("correo@ejemplo.com");

        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaRequestList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaRequestList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaRequestList, imagenes, caracteristicaRequestList, politicaBicicletaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        ValoracionRequestDto valoracionRequestDto = new ValoracionRequestDto(1L,  reservaRequestDto, 5, "Bicicleta en excelente estado", bicicletaRequestDto);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("correo@ejemplo.com");

        Optional<Usuario> usuarioOptional = Optional.of(usuarioMock);
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioOptional);

        // Act y Asserts
        assertThrows(ResourceNotFoundException.class, () -> {
            valoracionService.crear(valoracionRequestDto);
        });
    }

    @Test
    public void crearValoracionResourceAlreadyExistsException() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "electrica", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = List.of(caracteristicaBicicleta);
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        List<Politica> politicaBicicletaList = List.of(politica);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Valoracion valoracion = new Valoracion(1L, 5,usuario, "Muy bueno", LocalDate.of(2023, 12, 2));
        List<Valoracion> valoracionList = List.of(valoracion);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList, politicaBicicletaList,valoracionList,4.5,5L);
        Reserva reserva = new Reserva(1L, usuario, bicicleta, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03), valoracionList);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaRequestList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaRequestList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaRequestList, imagenes, caracteristicaRequestList, politicaBicicletaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        ValoracionRequestDto valoracionRequestDto = new ValoracionRequestDto(1L,  reservaRequestDto, 5, "Bicicleta en excelente estado", bicicletaRequestDto);

        //Acts
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("correo@ejemplo.com");
        //when(reservaService.isConcluida(any(Reserva.class))).thenReturn(true);
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Mockito.when(reservaRepository.findById(reservaRequestDto.getReservaId())).thenReturn(Optional.of(reserva));
        Mockito.when(valoracionRepository.existsByUsuarioAndReserva(usuario,reserva)).thenReturn(true);

        //Asserts
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            valoracionService.crear(valoracionRequestDto);
        });
    }

    @Test
    public void obtenerValoracionPorId(){
        //Arrange

        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Valoracion valoracion = new Valoracion(1L, 5, usuario, "Muy buena", LocalDate.now());
        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);
        ValoracionResponseDto valoracionEsperada = new ValoracionResponseDto(1L, usuarioEsperado, 5, "Muy buena", LocalDate.now());

        //Act
        Mockito.when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracion));
        ValoracionResponseDto valoracionResponseDto = valoracionService.buscarPorId(1L);

        // Asserts
        Assertions.assertEquals(valoracionEsperada.getValoracionId(), valoracionResponseDto.getValoracionId());
    }

    @Test
    public void obtenerValoracionPorIdThrowsResourceNotFoundException() {
        Mockito.when(valoracionRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> {
            valoracionService.buscarPorId(1L);
        });
    }

    @Test
    public void obtenerTodasLasValoraciones(){
        //Arrange

        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Valoracion valoracion = new Valoracion(1L, 5, usuario, "Muy buena", LocalDate.now());
        List<Valoracion> valoraciones = new ArrayList<>();
        valoraciones.add(valoracion);
        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);
        ValoracionResponseDto valoracionEsperada = new ValoracionResponseDto(1L, usuarioEsperado, 5, "Muy buena", LocalDate.now());
        List<ValoracionResponseDto> valoracionResponseLista = new ArrayList<>();
        valoracionResponseLista.add(valoracionEsperada);

        //Act
        Mockito.when(valoracionRepository.findAll()).thenReturn(valoraciones);
        List<ValoracionResponseDto> valoracionLista = valoracionService.listarTodos();

        // Asserts
        Assertions.assertEquals(valoracionResponseLista.size(), valoracionLista.size());
    }

    @Test
    public void listarTodasValoracionesThrowsResourceNotFoundException() {

        Mockito.when(valoracionRepository.findAll()).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> {
            valoracionService.listarTodos();
        });
    }

    @Test
    public void eliminarValoracion() {
        //Arrange
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Valoracion valoracion = new Valoracion(1L, 5, usuario, "Muy buena", LocalDate.now());
        //Act
        Mockito.when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracion));
        doNothing().when(valoracionRepository).delete(valoracion);
        valoracionService.borrarPorId(1L);
        // Asserts
        verify(valoracionRepository, times(1)).delete(valoracion);
    }

    @Test
    public void eliminarValoracionThrowsResourceNotFoundException() {

        Mockito.when(valoracionRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> {
            valoracionService.borrarPorId(1L);
        });
    }

    @Test
    public void actualizarReserva() throws MalformedURLException {
        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);

        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);

        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaRequestList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaRequestList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaRequestList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaRequestList, imagenes, caracteristicaRequestList, politicaBicicletaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 12, 23), LocalDate.of(2023, 12, 24));
        ValoracionRequestDto valoracionRequestDto = new ValoracionRequestDto(1L, reservaRequestDto, 5, "Muy buena bicicleta", bicicletaRequestDto);
        Valoracion valoracionEntity = new Valoracion(1L, 5, usuario, "Muy buena bicicleta", LocalDate.now());

        //Act
        Mockito.when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracionEntity));
        valoracionService.actualizar(valoracionRequestDto);

        // Asserts
        verify(valoracionRepository, times(1)).save(any(Valoracion.class));
    }

    @Test
    public void actualizarValoracionThrowsResourceNotFoundException() throws MalformedURLException {

        //Arrange
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        ImagenRequestDto imagenRequestDto = new ImagenRequestDto("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", imagenRequestDto);
        List<CategoriaBicicletaRequestDto> categoriaList = List.of(categoriaBicicletaRequestDto);
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "electrica", "icono");
        List<CaracteristicaBicicletaRequestDto> caracteristicaList = List.of(caracteristicaBicicletaRequestDto);
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Politica", "Descripcion politica");
        List<PoliticaRequestDto> politicaBicicletaRequestList = List.of(politicaRequestDto);
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList, politicaBicicletaRequestList);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto(1L, usuarioRequestDto, bicicletaRequestDto, LocalDate.of(2023, 07, 02), LocalDate.of(2023, 07, 03));
        ValoracionRequestDto valoracionRequestDto = new ValoracionRequestDto(1L, reservaRequestDto, 5, "Muy buena bicicleta", bicicletaRequestDto);
        Mockito.when(valoracionRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        //Act y  Asserts
        assertThrows(ResourceNotFoundException.class, () -> {
            valoracionService.actualizar(valoracionRequestDto);
        });
    }

}