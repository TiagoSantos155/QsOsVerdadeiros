import org.example.testejavafxmaven.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnidadeCurricularDAOTest {


    private UnidadeCurricularDAO unidadeCurricularDAO;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        unidadeCurricularDAO = new UnidadeCurricularDAO();

        try (var mockedStatic = mockStatic(DataBaseConnection.class)) {
            mockedStatic.when(DataBaseConnection::getConnection).thenReturn(mockConnection);
        }
    }

    @Test
    void testAdicionarUnidadeCurricular() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        unidadeCurricularDAO.adicionarUnidadeCurricular("Matemática", "Prova", 30);

        verify(mockPreparedStatement, times(1)).setString(1, "Matemática");
        verify(mockPreparedStatement, times(1)).setString(2, "Prova");
        verify(mockPreparedStatement, times(1)).setInt(3, 30);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testAtualizarUnidadeCurricular() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        unidadeCurricularDAO.atualizarUnidadeCurricular(1, "Física", "Trabalho", 25);

        verify(mockPreparedStatement, times(1)).setString(1, "Física");
        verify(mockPreparedStatement, times(1)).setString(2, "Trabalho");
        verify(mockPreparedStatement, times(1)).setInt(3, 25);
        verify(mockPreparedStatement, times(1)).setInt(4, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testListarUnidadesCurriculares() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mock(Statement.class));
        when(mockPreparedStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id_uc")).thenReturn(1);
        when(mockResultSet.getString("nome")).thenReturn("Matemática");
        when(mockResultSet.getString("tipo_avaliacao")).thenReturn("Prova");
        when(mockResultSet.getInt("numero_alunos")).thenReturn(30);

        List<UnidadeCurricular> unidades = unidadeCurricularDAO.listarUnidadesCurriculares();

        assertEquals(1, unidades.size());
        UnidadeCurricular unidade = unidades.get(0);
        assertEquals(1, unidade.getId());
        assertEquals("Matemática", unidade.getNome());
        assertEquals("Prova", unidade.getTipoAvaliacao());
        assertEquals(30, unidade.getNumeroAlunos());

        verify(mockResultSet, times(1)).next();
    }
}

