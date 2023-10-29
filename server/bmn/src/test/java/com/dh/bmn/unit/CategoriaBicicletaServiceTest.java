//package com.dh.bmn.unit;
//
//import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
//import com.dh.bmn.dtos.responses.CategoriaBicicletaResponseDto;
//import com.dh.bmn.entity.CategoriaBicicleta;
//import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
//import com.dh.bmn.exceptions.ResourceNotFoundException;
//import com.dh.bmn.repositories.ICategoriaBicicletaRepository;
//import com.dh.bmn.services.impl.CategoriaBicicletaService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class CategoriaBicicletaServiceTest {
//
//
//    @Mock
//    private ICategoriaBicicletaRepository categoriaBicicletaRepository;
//    @InjectMocks
//    private CategoriaBicicletaService categoriaBicicletaService;
//
//    @BeforeEach
//    public void setup() {
//
//        categoriaBicicletaRepository = mock(ICategoriaBicicletaRepository.class);
//        categoriaBicicletaService = new CategoriaBicicletaService(categoriaBicicletaRepository);
//    }
//
//    @Test
//    public void crearCategoriaBicicleta() {
//        //Arrange
//
//        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//        //Act
//        categoriaBicicletaService.crear(categoriaBicicletaRequestDto);
//
//        // Asserts
//        verify(categoriaBicicletaRepository, times(1)).save(any(CategoriaBicicleta.class));
//    }
//
//    @Test
//    public void crearCategoriaBicicletaThrowsResourceAlreadyExistsException() {
//        //Arrange
//        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//        //Act
//        when(categoriaBicicletaRepository.findByNombre(categoriaBicicletaRequestDto.getNombre())).thenReturn(Optional.of(categoriaBicicleta));
//
//        // Asserts
//        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
//            categoriaBicicletaService.crear(categoriaBicicletaRequestDto);
//        });
//
//    }
//
//
//    @Test
//    public void obtenerCategoriaBicicletaPorId() {
//        //Arrange
//
//        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//        CategoriaBicicletaResponseDto categoriaBicicletaEsperada = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//        //Act
//        Mockito.when(categoriaBicicletaRepository.findById(1L)).thenReturn(Optional.of(categoriaBicicleta));
//        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = categoriaBicicletaService.buscarPorId(1L);
//
//        // Asserts
//        Assertions.assertEquals(categoriaBicicletaEsperada.getCategoriaId(), categoriaBicicletaResponseDto.getCategoriaId());
//
//    }
//
//    @Test
//    public void obtenerCategoriaBicicletaPorIdThrowsResourceNotFoundException() {
//        Mockito.when(categoriaBicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
//
//        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            categoriaBicicletaService.buscarPorId(1L);
//        });
//    }
//
//    @Test
//    public void obtenerTodasCategoriasBicicletas() {
//        //Arrange
//        CategoriaBicicleta categoriaBicicletaEntity = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//        List<CategoriaBicicleta> categoriaBicicletas = new ArrayList<>();
//        categoriaBicicletas.add(categoriaBicicletaEntity);
//
//        CategoriaBicicletaResponseDto categoriaBicicletaEsperada = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//        List<CategoriaBicicletaResponseDto> categoriaBicicletaResponseLista = new ArrayList<>();
//        categoriaBicicletaResponseLista.add(categoriaBicicletaEsperada);
//
//        //Act
//        Mockito.when(categoriaBicicletaRepository.findAll()).thenReturn(categoriaBicicletas);
//        List<CategoriaBicicletaResponseDto> categoriaBicicletaLista = categoriaBicicletaService.listarTodos();
//
//        // Asserts
//        Assertions.assertEquals(categoriaBicicletaLista.size(), categoriaBicicletas.size());
//
//    }
//
//    @Test
//    public void listarTodasCategoriasBicicletasThrowsResourceNotFoundException() {
//
//        Mockito.when(categoriaBicicletaRepository.findAll()).thenThrow(ResourceNotFoundException.class);
//
//        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            categoriaBicicletaService.listarTodos();
//        });
//
//    }
//
//    @Test
//    public void eliminarCategoriaBicicleta() {
//        //Arrange
//
//        CategoriaBicicleta categoriaBicicletaEntity = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//        //Act
//        Mockito.when(categoriaBicicletaRepository.findById(1L)).thenReturn(Optional.of(categoriaBicicletaEntity));
//        doNothing().when(categoriaBicicletaRepository).delete(categoriaBicicletaEntity);
//
//        categoriaBicicletaService.borrarPorId(1L);
//
//        // Asserts
//        verify(categoriaBicicletaRepository, times(1)).delete(categoriaBicicletaEntity);
//    }
//
//    @Test
//    public void eliminarCategoriaBicicletaThrowsResourceNotFoundException() {
//
//        Mockito.when(categoriaBicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
//
//        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            categoriaBicicletaService.borrarPorId(1L);
//        });
//    }
//
//    @Test
//    public void actualizarCategoriaBicicleta() {
//        //Arrange
//        CategoriaBicicleta categoriaBicicletaEntity = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//        //Act
//        Mockito.when(categoriaBicicletaRepository.findById(1L)).thenReturn(Optional.of(categoriaBicicletaEntity));
//
//        categoriaBicicletaService.actualizar(categoriaBicicletaRequestDto);
//
//        // Asserts
//        verify(categoriaBicicletaRepository, times(1)).save(any(CategoriaBicicleta.class));
//    }
//
//        @Test
//        public void actualizarCategoriaBicicletaThrowsResourceNotFoundException() {
//
//            CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
//
//
//            Mockito.when(categoriaBicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
//
//            Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//                categoriaBicicletaService.actualizar(categoriaBicicletaRequestDto);
//            });
//        }
//
//}
