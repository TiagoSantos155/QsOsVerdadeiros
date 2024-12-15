import org.example.testejavafxmaven.Avaliacoes;
import org.example.testejavafxmaven.AvaliacoesDAO;
import org.example.testejavafxmaven.DataBaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvaliacoesDAOTest {

    private AvaliacoesDAO avaliacoesDAO;

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
        avaliacoesDAO = new AvaliacoesDAO();

        // Mock da classe DataBaseConnection
        try (var mockedStatic = mockStatic(DataBaseConnection.class)) {
            mockedStatic.when(DataBaseConnection::getConnection).thenReturn(mockConnection);
        }
    }

    @Test
    void testInserirAvaliacao() throws Exception {
        Avaliacoes avaliacao = new Avaliacoes(0, "Prova Final", 0.3,
                Timestamp.valueOf("2024-01-01 10:00:00"), "Sala A1", true, false, 1, 2);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        avaliacoesDAO.inserirAvaliacao(avaliacao);

        verify(mockPreparedStatement, times(1)).setString(1, "Prova Final");
        verify(mockPreparedStatement, times(1)).setDouble(2, 0.3);
        verify(mockPreparedStatement, times(1)).setTimestamp(3, Timestamp.valueOf("2024-01-01 10:00:00"));
        verify(mockPreparedStatement, times(1)).setString(4, "Sala A1");
        verify(mockPreparedStatement, times(1)).setBoolean(5, true);
        verify(mockPreparedStatement, times(1)).setBoolean(6, false);
        verify(mockPreparedStatement, times(1)).setInt(7, 1);
        verify(mockPreparedStatement, times(1)).setObject(8, 2);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testAtualizarAvaliacao() throws Exception {
        Avaliacoes avaliacao = new Avaliacoes(1, "Exame Final", 0.5,
                Timestamp.valueOf("2024-06-01 14:00:00"), "Sala B2", false, true, 2, null);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        avaliacoesDAO.atualizarAvaliacao(avaliacao);

        verify(mockPreparedStatement, times(1)).setString(1, "Exame Final");
        verify(mockPreparedStatement, times(1)).setDouble(2, 0.5);
        verify(mockPreparedStatement, times(1)).setTimestamp(3, Timestamp.valueOf("2024-06-01 14:00:00"));
        verify(mockPreparedStatement, times(1)).setString(4, "Sala B2");
        verify(mockPreparedStatement, times(1)).setBoolean(5, false);
        verify(mockPreparedStatement, times(1)).setBoolean(6, true);
        verify(mockPreparedStatement, times(1)).setInt(7, 2);
        verify(mockPreparedStatement, times(1)).setObject(8, null);
        verify(mockPreparedStatement, times(1)).setInt(9, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testDeletarAvaliacao() throws Exception {
        int idAvaliacao = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        avaliacoesDAO.deletarAvaliacao(idAvaliacao);

        verify(mockPreparedStatement, times(1)).setInt(1, idAvaliacao);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testListarAvaliacoes() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id_avaliacao")).thenReturn(1);
        when(mockResultSet.getString("tipo_avaliacao")).thenReturn("Prova Final");
        when(mockResultSet.getDouble("ponderacao")).thenReturn(0.4);
        when(mockResultSet.getTimestamp("data_hora")).thenReturn(Timestamp.valueOf("2024-05-01 09:00:00"));
        when(mockResultSet.getString("sala")).thenReturn("Sala C3");
        when(mockResultSet.getBoolean("horario_habitual")).thenReturn(true);
        when(mockResultSet.getBoolean("requer_computador")).thenReturn(false);
        when(mockResultSet.getInt("id_uc")).thenReturn(3);
        when(mockResultSet.getObject("id_epoca", Integer.class)).thenReturn(1);

        List<Avaliacoes> avaliacoes = avaliacoesDAO.listarAvaliacoes();

        assertEquals(1, avaliacoes.size());
        Avaliacoes avaliacao = avaliacoes.get(0);
        assertEquals(1, avaliacao.getId());
        assertEquals("Prova Final", avaliacao.getTipoAvaliacao());
        assertEquals(0.4, avaliacao.getPonderacao());
        assertEquals(Timestamp.valueOf("2024-05-01 09:00:00"), avaliacao.getDataHora());
        assertEquals("Sala C3", avaliacao.getSala());
        assertTrue(avaliacao.isHorarioHabitual());
        assertFalse(avaliacao.isRequerComputador());
        assertEquals(3, avaliacao.getIdUc());
        assertEquals(1, avaliacao.getIdEpoca());
    }

    @Test
    void testFiltrarPorNecessidadeComputador() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id_avaliacao")).thenReturn(1);
        when(mockResultSet.getString("tipo_avaliacao")).thenReturn("Prova Final");
        when(mockResultSet.getDouble("ponderacao")).thenReturn(0.3);
        when(mockResultSet.getTimestamp("data_hora")).thenReturn(Timestamp.valueOf("2024-01-01 10:00:00"));
        when(mockResultSet.getString("sala")).thenReturn("Sala D1");
        when(mockResultSet.getBoolean("horario_habitual")).thenReturn(false);
        when(mockResultSet.getBoolean("requer_computador")).thenReturn(true);
        when(mockResultSet.getInt("id_uc")).thenReturn(4);
        when(mockResultSet.getObject("id_epoca", Integer.class)).thenReturn(2);

        List<Avaliacoes> avaliacoes = avaliacoesDAO.filtrarPorNecessidadeComputador(true);

        assertEquals(1, avaliacoes.size());
        Avaliacoes avaliacao = avaliacoes.get(0);
        assertTrue(avaliacao.isRequerComputador());
    }
}
