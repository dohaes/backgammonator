package backgammonator.test.game;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;
import backgammonator.impl.game.GameManager;
import backgammonator.lib.game.Game;
import backgammonator.lib.game.GameOverStatus;


/**
 * @author Georgi Andreev
 */
public class NewGameTestCase
    extends TestCase
{

    private static File testDir = new File("testGameWithProtocol");
    private static String samplesJava = "sample/backgammonator/sample/player";

    private String fileName1;
    private String fileName2;


    /**
     * Test normal.
     */
    public void testNormal()
    {
        try
        {
            copy("SamplePlayer", "SamplePlayer");
            Game game = GameManager.newGame(fileName1, fileName2, false);
            GameOverStatus status = null;

            try
            {
                status = game.start();
            }
            catch (Throwable t)
            {
                fail("Unexpected exception was thrown : " + t);
            }

            assertNotNull(status);
            assertEquals(GameOverStatus.NORMAL, status);
            assertTrue(game.getWinner().getName().startsWith("sampleplayer"));

        }
        finally
        {
            cleanup();
        }
    }


    private void copy(String name1, String name2)
    {
        if (!testDir.exists())
            testDir.mkdirs();
        boolean index = name1.equals(name2);
        String dirName1 = testDir.getAbsolutePath() + File.separatorChar + name1.toLowerCase()
                          + (index ? "1" : "");
        String dirName2 = testDir.getAbsolutePath() + File.separatorChar + name2.toLowerCase()
                          + (index ? "2" : "");

        File dir1 = new File(dirName1);
        File dir2 = new File(dirName2);
        dir1.mkdirs();
        dir2.mkdirs();

        fileName1 = dir1.getAbsolutePath() + File.separatorChar + name1 + ".java";
        fileName2 = dir2.getAbsolutePath() + File.separatorChar + name2 + ".java";

        String samplesPath = new File(samplesJava.replace('/', File.separatorChar)).getAbsolutePath();
        try
        {
            copyFile(samplesPath + File.separatorChar + name1 + ".java", fileName1);
            copyFile(samplesPath + File.separatorChar + name2 + ".java", fileName2);
        }
        catch (IOException e)
        {
            fail("Unexpected exception while cpopying files : " + e.getMessage());
        }

    }


    private void copyFile(String from, String to)
        throws IOException
    {
        BufferedReader fr = new BufferedReader(new FileReader(new File(from)));
        BufferedWriter fw = new BufferedWriter(new FileWriter(new File(to)));
        String line = null;
        try
        {
            while ((line = fr.readLine()) != null)
            {
                if (line.startsWith("package"))
                    continue;
                fw.write(line + "\n");
            }
        }
        finally
        {
            fr.close();
            fw.flush();
            fw.close();
        }

    }


    private void cleanup()
    {
        if (testDir.exists())
            assertTrue("Could not delete files", delete(testDir));
    }


    private boolean delete(File file)
    {
        if (file.isFile())
            return file.delete();
        File[] files = file.listFiles();
        if (files == null || files.length == 0)
            return file.delete();
        for (int i = 0; i < files.length; i++)
        {
            delete(files[i]);
        }
        return file.delete();
    }
}
