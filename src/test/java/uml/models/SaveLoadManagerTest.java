package uml.models;

import junit.framework.TestCase;
import uml.controls.RelationshipFactory;
import uml.controls.SaveLoadManager;
import java.io.File;

public class SaveLoadManagerTest extends TestCase {

    private SaveLoadManager slm;
    private Canvas canvas;

    @Override
    public void setUp() throws Exception {
        slm = SaveLoadManager.getInstance();
        canvas = new Canvas();

        for(int i = 0; i < 20; i++)
        {
            canvas.addClassBox(new ClassBox());
            canvas.addRelationship(RelationshipFactory.getFromType("association"));
        }
    }

    public void testSave() throws Exception {
        File temp = new File(slm.getFileName());
        temp.deleteOnExit();
        assertFalse(temp.exists());
        slm.save(canvas.getRelationships(), canvas.getClassBoxes());
        assertTrue(temp.exists());
    }

    public void testLoad() throws Exception {
    }

    public void testGetSetFileName() throws Exception {
        String[] testNames = { "test", "test.sav", "test.jpg", "",
            "abcdefghijklmnopqrstuvwxyzABCDEFGIJKLMNOPQRSTUVWKYZ"};
        for (String s : testNames)
        {
            slm.setFileName(s);
            assertEquals(slm.getFileName(), s);
        }
    }

    public void testIsValidFileName() throws Exception {
        String[] validNames = {"1.sav", "dog.sav", "TestName3000.sav"};
        String[] invalidNames = {".sav", "", "\t.sav", "file.exe", "00000"};

        for (String s : validNames)
        {
            assertTrue(slm.isValidFileName(s));
        }

        for (String s : invalidNames)
        {
            assertFalse(slm.isValidFileName(s));
        }
    }

    public void testIsValidImageFile() throws Exception {
        String[] validNames = {"1.jpg", "dog.jpg", "TestName3000.jpg"};
        String[] invalidNames = {".sav", ".jpg", "\t.jpg", "file.exe", "00000", "word.png"};

        for (String s : validNames)
        {
            assertTrue(slm.isValidImageFile(s));
        }

        for (String s : invalidNames)
        {
            assertFalse(slm.isValidImageFile(s));
        }
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
