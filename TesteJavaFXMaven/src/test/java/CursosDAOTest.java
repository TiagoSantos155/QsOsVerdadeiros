import org.example.testejavafxmaven.Cursos;
import org.example.testejavafxmaven.CursosDAO;
import org.example.testejavafxmaven.DataBaseConnection;
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

class CursosDAOTest {

    private CursosDAO cursosDAO;

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
        cursosDAO = new CursosDAO();

        // Mock da classe DataBaseConnection
        try (var mockedStatic = mockStatic(DataBaseConnection.class)) {
            mockedStatic.when(DataBaseConnection::getConnection).thenReturn(mockConnection);
        }
    }

    @Test
    void testAdicionarCurso() throws Exception {
        String nomeCurso = "Engenharia Informática";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        cursosDAO.adicionarCurso(nomeCurso);

        verify(mockPreparedStatement, times(1)).setString(1, nomeCurso);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testListarCursos() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id_curso")).thenReturn(1);
        when(mockResultSet.getString("nome")).thenReturn("Engenharia Informática");

        List<Cursos> cursos = cursosDAO.listarCursos();

        assertEquals(1, cursos.size());
        Cursos curso = cursos.get(0);
        assertEquals(1, curso.getId());
        assertEquals("Engenharia Informática", curso.getNome());

        verify(mockResultSet, times(1)).next();
    }

    @Test
    void testUpdateCurso() throws Exception {
        Cursos curso = new Cursos(1, "Engenharia de Software");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        cursosDAO.update(curso);

        verify(mockPreparedStatement, times(1)).setString(1, "Engenharia de Software");
        verify(mockPreparedStatement, times(1)).setInt(2, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testDeleteCurso() throws Exception {
        int idCurso = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        cursosDAO.delete(idCurso);

        verify(mockPreparedStatement, times(1)).setInt(1, idCurso);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}