import org.example.testejavafxmaven.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Mockito.*;

public class SemestreDAOTest {

    private SemestreDAO semestreDAO;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        semestreDAO = new SemestreDAO();

        try (var mockedStatic = mockStatic(DataBaseConnection.class)) {
            mockedStatic.when(DataBaseConnection::getConnection).thenReturn(mockConnection);
        }
    }

    @Test
    void testSalvarSemestres() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        semestreDAO.salvarSemestres("2024-01-01", "2024-06-30", "2024-07-01", "2024-12-31");

        verify(mockPreparedStatement, times(1)).setString(1, "2024-01-01");
        verify(mockPreparedStatement, times(1)).setString(2, "2024-06-30");
        verify(mockPreparedStatement, times(1)).setString(3, "2024-07-01");
        verify(mockPreparedStatement, times(1)).setString(4, "2024-12-31");
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}
