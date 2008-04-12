package lt.wordsmith;

import java.util.HashMap;
import java.util.Set;
import net.didion.jwnl.data.POS;

public interface GeneratorListener
{
	void generateSentence(HashMap<POS, Set<String>> _ms);
}
