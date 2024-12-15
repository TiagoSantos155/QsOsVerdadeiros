import org.example.testejavafxmaven.DataBaseConnection;
import org.example.testejavafxmaven.Sala;
import org.example.testejavafxmaven.SalaDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalaDAOTest {

    private SalaDAO salaDAO;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        salaDAO = new SalaDAO();

        // Mock da classe DataBaseConnection
        try (var mockedStatic = mockStatic(DataBaseConnection.class)) {
            mockedStatic.when(DataBaseConnection::getConnection).thenReturn(mockConnection);
        }
    }

    @Test
    void testInserirSala() throws Exception {
        Sala sala = new Sala(0, "Sala A", 30, 10);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        salaDAO.inserirSala(sala);

        verify(mockPreparedStatement, times(1)).setString(1, "Sala A");
        verify(mockPreparedStatement, times(1)).setInt(2, 30);
        verify(mockPreparedStatement, times(1)).setInt(3, 10);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testAtualizarSala() throws Exception {
        Sala sala = new Sala(1, "Sala B", 40, 20);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        salaDAO.atualizarSala(sala);

        verify(mockPreparedStatement, times(1)).setString(1, "Sala B");
        verify(mockPreparedStatement, times(1)).setInt(2, 40);
        verify(mockPreparedStatement, times(1)).setInt(3, 20);
        verify(mockPreparedStatement, times(1)).setInt(4, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testDeletarSala() throws Exception {
        int idSala = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        salaDAO.deletarSala(idSala);

        verify(mockPreparedStatement, times(1)).setInt(1, idSala);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testListarSalas() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id_sala")).thenReturn(1);
        when(mockResultSet.getString("nome")).thenReturn("Sala C");
        when(mockResultSet.getInt("capacidade")).thenReturn(50);
        when(mockResultSet.getInt("computadores")).thenReturn(25);

        List<Sala> salas = salaDAO.listarSalas();

        assertEquals(1, salas.size());
        Sala sala = salas.get(0);
        assertEquals(1, sala.getId());
        assertEquals("Sala C", sala.getNome());
        assertEquals(50, sala.getCapacidade());
        assertEquals(25, sala.getComputadores());

        verify(mockResultSet, times(1)).next();
    }
}