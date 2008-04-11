package lt.wordsmith;

import java.util.Collection;

public interface GeneratorListener
{
	void generateSentence(Collection<WordInfo> c);
}
